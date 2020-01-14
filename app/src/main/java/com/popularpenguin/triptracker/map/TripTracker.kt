package com.popularpenguin.triptracker.map

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.SphericalUtil
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.*
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.File
import java.util.*

/** Class to combine the map and location functions to draw a trip session */
class TripTracker(private val fragment: Fragment) : OnMapReadyCallback, UserLocation.UserLocationListener {

    companion object {
        const val REQUEST_PHOTO = 0
    }

    private val jobList = mutableListOf<Job>()
    private val location = UserLocation(fragment.requireContext())
    private val locationList = mutableListOf<LatLng>()
    private val serviceConnection = TrackerNotification.getServiceConnection(fragment.requireContext())
    private val photoMarkerList = mutableListOf<LatLng>()
    private val uriList = mutableListOf<Uri>()

    private lateinit var map: GoogleMap
    private lateinit var infoTextView: TextView
    private lateinit var photoFile: File

    private var distance = 0.0
    private var isMapReady = false
    private var isRefreshed = true
    private var isRunning = false
    private var isFinished = false

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

    fun onDestroy() {
        if (!isFinished && isRunning) {
            location.apply {
                removeListener(this@TripTracker)
                stopLocationUpdates()
            }
            fragment.requireActivity().apply {
                unbindService(serviceConnection)
                stopService(Intent(this, TrackerNotification::class.java))
            }

            // delete all photos from internal storage since the trip has been canceled, there is now no way to access
            // they will still remain in gallery
            for (photoUri in uriList) {
                FileUtils.deletePhoto(fragment.requireContext(), photoUri)
            }
        }

        for (job in jobList) {
            if (job.isActive) {
                job.cancel()
            }
        }
    }

    fun addCameraListener(cameraView: View) {
        cameraView.setOnClickListener {
            val context = fragment.requireContext()
            val cameraLoader = CameraLoader(fragment.requireActivity(), it)
            val capture = cameraLoader.captureIntent

            // No camera apps on phone
            if (!cameraLoader.resolveActivity) {
                it.isEnabled = false
            }
            // No camera on device or no permission
            if (!cameraLoader.cameraReady) {
                return@setOnClickListener
            }

            // Tracker must be running in order to take and save photos
            if (!isRunning) {
                TripSnackbar(cameraView, R.string.snackbar_tracker_camera, Snackbar.LENGTH_SHORT)
                    .show()

                return@setOnClickListener
            }

            photoFile = FileUtils.getPhotoFile(context)
            val photoUri = FileUtils.getPhotoUri(context, photoFile)
            cameraLoader.grantUriPermission(photoUri)

            fragment.startActivityForResult(capture, REQUEST_PHOTO)
        }
    }

    fun addControlListener(controlView: View, hintView: View? = null) {
        controlView.setOnClickListener {
            hintView?.visibility = View.GONE
            // show the info text for the first time
            infoTextView.visibility = View.VISIBLE

            if (!isRunning) {
                location.apply {
                    addListener(this@TripTracker)
                    startLocationUpdates()
                }

                fragment.requireActivity().apply {
                    this.bindService(
                            Intent(this, TrackerNotification::class.java),
                            serviceConnection,
                            Context.BIND_AUTO_CREATE
                    )
                }

                it.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(fragment.requireContext(), R.color.red)
                )

                if (it is FloatingActionButton) {
                    it.setImageDrawable(
                        fragment.resources.getDrawable(android.R.drawable.ic_menu_edit, null)
                    )
                }
            }

            if (isRunning) {
                isRunning = false

                location.apply {
                    removeListener(this@TripTracker)
                    stopLocationUpdates()
                }

                showSaveDialog()
            }

            isRunning = true
        }
    }

    fun addZoomListener(zoomView: View) {
        zoomView.setOnClickListener {
            if (isRunning && locationList.isNotEmpty()) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(locationList.last(), UserLocation.ZOOM))
            }
        }
    }

    fun addMapTypeListener(typeView: View) {
        typeView.setOnClickListener {
            if (map.mapType == GoogleMap.MAP_TYPE_TERRAIN) {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
            } else {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
        }
    }

    fun setInfoTextView(view: TextView) {
        infoTextView = view
        infoTextView.visibility = View.GONE // Initially, the view is hidden from the user until the map is ready
    }

    fun storePhoto() {
        val context = fragment.requireContext()
        val photoUri = FileUtils.getPhotoUri(context, photoFile)
        uriList.add(photoUri)

        context.revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        val savePhotoJob = CoroutineScope(IO).launch {
            ImageLoader.storePhoto(context, photoUri, photoFile)

            if (locationList.isNotEmpty()) {
                photoMarkerList.add(locationList.last())
            }
        }

        jobList.add(savePhotoJob)
    }

    private fun showSaveDialog() {
        MaterialDialog(fragment.requireContext()).show {
            input(hintRes = R.string.dialog_save_hint)
            cornerRadius(10f)
            negativeButton(R.string.dialog_save_cancel) {
                isRunning = true
            }
            positiveButton(R.string.dialog_save_save) { dialog ->
                val description = dialog.getInputField()
                    .text
                    .toString()

                dialog.dismiss()

                commitToDatabase(description)

                // shut down the notification service before detaching the fragment
                fragment.requireActivity().apply {
                    unbindService(serviceConnection)
                    stopService(Intent(this, TrackerNotification::class.java))
                }

                isFinished = true

                ScreenNavigator(fragment.requireActivity().supportFragmentManager).loadTripList()
            }
        }
    }

    private fun commitToDatabase(dialogDescription: String) {
        val insertTripJob = CoroutineScope(IO).launch {
            val trip = Trip().apply {
                date = Date()
                description = dialogDescription
                photoMarkerList = this@TripTracker.photoMarkerList
                points = locationList
                totalDistance = this@TripTracker.distance
                uriList = this@TripTracker.uriList
            }

            AppDatabase.get(fragment.requireContext())
                .dao()
                .insert(trip)
        }

        jobList.add(insertTripJob)

        Toast.makeText(
                fragment.requireContext(),
                fragment.getString(R.string.tracker_save),
                Toast.LENGTH_LONG
        ).show()
    }

    private fun computeTotalDistance(): Double {
        val totalDistance = SphericalUtil.computeLength(locationList)

        return totalDistance * 0.000621371 // meters to miles
    }

    override fun onLocationUpdated(latLng: LatLng, zoom: Float) {
        if (!isMapReady) return

        locationList.add(latLng)

        map.apply {
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
            } else {
                // this is the first location update, so zoom in on the user's location
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
                // marker denoting the start of the trip
                addMarker(MarkerOptions()
                    .position(latLng)
                    .title(fragment.getString(R.string.marker_start))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
            }
        }

        distance = computeTotalDistance()

        val infoText = "${fragment.getString(R.string.text_distance)}" +
                "${distance.toString().take(6)} ${fragment.getString(R.string.text_distance_units)}"
        infoTextView.text = infoText

        isRefreshed = false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            mapType = GoogleMap.MAP_TYPE_TERRAIN

            try {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = false
                uiSettings.isMapToolbarEnabled = false
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            setOnCameraMoveStartedListener {
                ViewAnimationUtils.hideInfoView(infoTextView)
            }
            setOnCameraIdleListener {
                ViewAnimationUtils.showInfoView(infoTextView)
            }
        }
        isMapReady = true
    }
}