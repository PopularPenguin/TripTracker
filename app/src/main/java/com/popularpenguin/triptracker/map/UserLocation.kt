package com.popularpenguin.triptracker.map

import android.content.Context
import com.google.android.gms.location.*

class UserLocation(context: Context) {

    companion object {
        const val INTERVAL = 10000L
        const val FASTEST_INTERVAL = 5000L
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest = LocationRequest.create().apply {
        interval = INTERVAL
        fastestInterval = FASTEST_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                // Update UI with location data
                latitude = location.latitude
                longitude = location.longitude
            }
        }
    }

    private val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    private val client: SettingsClient = LocationServices.getSettingsClient(context)
    private val task = client.checkLocationSettings(builder.build()).apply {
        addOnSuccessListener {
            // Initialize location requests here
        }
        addOnFailureListener {

        }
    }

    var latitude = 0.0
        private set
    var longitude = 0.0
        private set

    fun startLocationUpdates() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    latitude = it.latitude
                    longitude = it.longitude
                } else {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun getLastLocation() {

    }
}