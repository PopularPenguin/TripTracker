package com.popularpenguin.triptracker.map

import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.popularpenguin.triptracker.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** Class to combine the map and location functions to draw a trip session */
class TripTracker(fragment: Fragment) : OnMapReadyCallback {

    private val location = UserLocation(fragment.requireContext())
    private lateinit var map: GoogleMap

    init {
        val mapFragment = fragment.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    fun onResume() {
        location.startLocationUpdates()
    }

    fun onPause() {
        location.stopLocationUpdates()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // TODO: Remove this code later, test functions here
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
        }
    }
}