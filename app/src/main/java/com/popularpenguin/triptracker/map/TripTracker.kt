package com.popularpenguin.triptracker.map

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ScreenNavigator
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

/** Class to combine the map and location functions to draw a trip session */
class TripTracker(private val fragment: Fragment) : OnMapReadyCallback, UserLocation.UserLocationListener {

    private val dao = AppDatabase.get(fragment.requireActivity().applicationContext).dao()
    private val jobList = mutableListOf<Job>()
    private val location = UserLocation(fragment.requireContext())
    private val locationList = mutableListOf<LatLng>()
    private val notification = TrackerNotification(fragment.requireContext())

    private lateinit var map: GoogleMap

    private var distance = 0.0 // TODO: Compute distance
    private var isMapReady = false
    private var isRefreshed = true
    private var isRunning = false

    init {
        val mapFragment = fragment.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    fun onResume() {
        if (!isRunning) return

        location.apply {
            addListener(this@TripTracker)
            startLocationUpdates()
        }
    }

    fun onPause() {
        if (!isRunning) return

        location.apply {
            removeListener(this@TripTracker)
            stopLocationUpdates()
        }

        jobList.forEach { it.cancel() }
    }

    fun addControlListener(controlView: View) {
        controlView.setOnClickListener {
            if (!isRunning) {
                location.apply {
                    addListener(this@TripTracker)
                    startLocationUpdates()
                }

                notification.createNotification()

                if (it is Button) {
                    it.text = "Stop"
                }
            }

            if (isRunning) {
                location.apply {
                    removeListener(this@TripTracker)
                    stopLocationUpdates()

                    showSaveDialog()
                }
            }

            isRunning = !isRunning
        }
    }

    private fun showSaveDialog() {
        val dialog = SaveDialog(fragment.requireContext()).apply {
            setCancelButtonOnClickListener {
                location.apply {
                    addListener(this@TripTracker)
                    startLocationUpdates()
                }

                this.dismiss()
            }
            setSaveButtonOnClickListener {
                notification.cancelNotification()

                commitToDatabase(this.getDescription())

                this.dismiss()

                ScreenNavigator(fragment.activity!!.supportFragmentManager).loadTripList()
            }
        }.show()
    }

    private fun commitToDatabase(dialogDescription: String) {
        val job = GlobalScope.launch (Dispatchers.IO) {
            val trip = Trip().apply {
                date = Date()
                description = dialogDescription
                points = locationList
                totalDistance = this@TripTracker.distance
            }

            dao.insert(trip)
        }

        job.start()
        jobList.add(job)
    }

    private fun computeTotalDistance() {
        // TODO: Create function to compute the total latLng distance
    }

    override fun onLocationUpdated(latLng: LatLng, zoom: Float) {
        if (!isMapReady) return

        locationList.add(latLng)

        map.apply {
            animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
            if (locationList.size > 1) {
                val polylineOptions = PolylineOptions().apply {
                    width(5.0f)
                }
                // TripTracker has been destroyed and recreated: Add all lines
                if (isRefreshed) {
                    addPolyline(polylineOptions.addAll(locationList))
                } else {
                    addPolyline(polylineOptions.add(locationList[locationList.size - 2], latLng))
                }
            }
        }

        Log.d("TripTracker", "locationList.size = ${locationList.size}")

        isRefreshed = false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            try {
                isMyLocationEnabled = true
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
        isMapReady = true

        /*
        // Add a marker in Sydney, Australia, and move the camera
        val sydneyLatLng = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().apply {
            position(sydneyLatLng)
            title("Marker in Sydney")
        })
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyLatLng))
        GlobalScope.launch(Dispatchers.Main) {
            delay(11000L)

            map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
        } */
    }
}