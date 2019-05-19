package com.popularpenguin.triptracker.map

import android.content.Context
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng

class UserLocation(context: Context) {

    companion object {
        const val INTERVAL = 30_000L
        const val FASTEST_INTERVAL = 10_000L
        const val ZOOM = 15.0f
    }

    interface UserLocationListener {
        fun onLocationUpdated(latLng: LatLng, zoom: Float)
    }

    private var userLocationListeners = mutableListOf<UserLocationListener>()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest = LocationRequest.create().apply {
        interval = INTERVAL
        fastestInterval = FASTEST_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return

            val location = locationResult.lastLocation
            val latLng = LatLng(location.latitude, location.longitude)

            userLocationListeners.forEach { it.onLocationUpdated(latLng, ZOOM) }
        }
    }

    fun startLocationUpdates() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                } else {
                    // TODO: Handle
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun addListener(listener: UserLocationListener) {
        userLocationListeners.add(listener)
    }

    fun removeListener(listener: UserLocationListener) {
        userLocationListeners.remove(listener)
    }
}