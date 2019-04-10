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

class MapFragment: Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(): MapFragment {
            val fragment = MapFragment()

            return fragment
        }
    }

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map

        // Add a marker in Sydney, Australia, and move the camera
        val sydneyLatLng = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().apply {
            position(sydneyLatLng)
            title("Marker in Sydney")
        })
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyLatLng))
    }
}