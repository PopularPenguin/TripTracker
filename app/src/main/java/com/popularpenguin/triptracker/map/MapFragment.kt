package com.popularpenguin.triptracker.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class MapFragment: Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(): MapFragment {
            val fragment = MapFragment()

            return fragment
        }
    }

    private lateinit var mLocation: UserLocation
    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mLocation = UserLocation(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()

        mLocation.startLocationUpdates()
    }

    override fun onPause() {
        mLocation.stopLocationUpdates()

        super.onPause()
    }

    override fun onMapReady(map: GoogleMap) {
        // TODO: Test functionality, move out of class to a new TripTracker class
        mMap = map

        // Add a marker in Sydney, Australia, and move the camera
        val sydneyLatLng = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().apply {
            position(sydneyLatLng)
            title("Marker in Sydney")
        })
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyLatLng))
        GlobalScope.launch(Dispatchers.Main) {
            delay(11000L)

            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(mLocation.latitude, mLocation.longitude)))
        }

    }
}