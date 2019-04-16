package com.popularpenguin.triptracker.map

import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.coroutines.Job

/** Class to combine the map and location functions to draw a trip session */
class TripTracker(fragment: Fragment) : OnMapReadyCallback, UserLocation.UserLocationListener {

    private val dao = AppDatabase.get(fragment.requireActivity().applicationContext).dao()
    private val jobs = mutableListOf<Job>()
    private val location = UserLocation(fragment.requireContext())

    private lateinit var map: GoogleMap

    private var isMapReady = false
    private var isRefreshed = true

    init {
        val mapFragment = fragment.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    fun onResume() {
        location.apply {
            addListener(this@TripTracker)
            startLocationUpdates()
        }
    }

    fun onPause() {
        location.apply {
            removeListener(this@TripTracker)
            stopLocationUpdates()
        }
    }

    override fun onLocationUpdated(latLng: List<LatLng>, zoom: Float) {
        if (!isMapReady) return

        map.apply {
            animateCamera(CameraUpdateFactory.newLatLngZoom(latLng.last(), zoom))
            if (latLng.size > 1) {
                // TripTracker has been destroyed and recreated: Add all lines
                if (isRefreshed) {
                    addPolyline(PolylineOptions().addAll(latLng))
                } else {
                    addPolyline(PolylineOptions().add(latLng.last()))
                }
            }
        }

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