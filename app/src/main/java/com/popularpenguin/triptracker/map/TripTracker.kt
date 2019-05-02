package com.popularpenguin.triptracker.map

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.SphericalUtil
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ScreenNavigator
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import com.popularpenguin.triptracker.singletrip.SingleTripFragment
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

/** Class to combine the map and location functions to draw a trip session */
class TripTracker(private val fragment: Fragment) : OnMapReadyCallback, UserLocation.UserLocationListener {

    private val REQUEST_PHOTO = 0

    private val dao = AppDatabase.get(fragment.requireActivity().applicationContext).dao()
    private val jobList = mutableListOf<Job>()
    private val location = UserLocation(fragment.requireContext())
    private val locationList = mutableListOf<LatLng>()
    private val notification = TrackerNotification(fragment.requireContext())
    private val photoList = mutableListOf<String>()

    private lateinit var map: GoogleMap
    private lateinit var photoFile: File

    private var distance = 0.0
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

    fun addCameraListener(cameraView: View) {
        cameraView.setOnClickListener {
            val filesDir = fragment.requireContext().filesDir
            val fileName = "${System.currentTimeMillis()}.jpg"
            photoFile = File(filesDir, fileName)
            val capture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val packageManager = fragment.requireActivity().packageManager

            if (capture.resolveActivity(packageManager) == null) {
                it.isEnabled = false
            }

            val uri = FileProvider.getUriForFile(
                fragment.requireActivity(),
                "com.popularpenguin.triptracker.fileprovider",
                photoFile
            )
            capture.putExtra(MediaStore.EXTRA_OUTPUT, uri)

            val cameraActivities = packageManager.queryIntentActivities(
                capture,
                PackageManager.MATCH_DEFAULT_ONLY
            )

            for (activity in cameraActivities) {
                fragment.requireActivity().grantUriPermission(
                    activity.activityInfo.packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }

            fragment.startActivityForResult(capture, REQUEST_PHOTO)
        }
    }

    fun addControlListener(controlView: View) {
        controlView.setOnClickListener {
            if (!isRunning) {
                location.apply {
                    addListener(this@TripTracker)
                    startLocationUpdates()
                }

                notification.createNotification()

                it.backgroundTintList = ColorStateList.valueOf(fragment.resources.getColor(R.color.red))
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

    fun addZoomListener(zoomView: View) {
        zoomView.setOnClickListener {
            if (isRunning) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(locationList.last(), UserLocation.ZOOM))
            }
        }
    }

    fun storePhoto() {
        // TODO: Move to coroutine if this operation is noticably slow?
        val activity = fragment.requireActivity()
        val uri = FileProvider.getUriForFile(
            activity,
            "com.popularpenguin.triptracker.fileprovider",
            photoFile
        )

        activity.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        val bitmap = BitmapFactory.decodeFile(photoFile.path)
        val outputStream = FileOutputStream(photoFile)

        // rotate the bitmap 90 degrees
        val matrix = Matrix().apply { postRotate(90.0f) }
        val finalBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        // compress and save to gallery
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)
        MediaStore.Images.Media.insertImage(
            activity.contentResolver,
            finalBitmap,
            photoFile.name,
            photoFile.name
        )

        photoList.add("file://${photoFile.absolutePath}")

        outputStream.close()

        // TODO: Get current location and add a marker for where the photo was taken?
    }

    private fun showSaveDialog() {
        SaveDialog(fragment.requireContext()).apply {
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

                ScreenNavigator(fragment.requireActivity().supportFragmentManager).loadTripList()
            }
        }.show()
    }

    private fun commitToDatabase(dialogDescription: String) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            val trip = Trip().apply {
                date = Date()
                description = dialogDescription
                photoList = this@TripTracker.photoList
                points = locationList
                totalDistance = this@TripTracker.distance
            }

            dao.insert(trip)
        }

        job.start()
        jobList.add(job)

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
                animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
            }
        }

        distance = computeTotalDistance()

        Log.d("TripTracker", "locationList.size = ${locationList.size}")

        isRefreshed = false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            try {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = false
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
        isMapReady = true
    }
}