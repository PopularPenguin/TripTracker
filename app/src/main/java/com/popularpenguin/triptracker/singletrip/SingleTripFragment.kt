package com.popularpenguin.triptracker.singletrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.PolylineOptions
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.map.UserLocation
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleTripFragment: Fragment(), OnMapReadyCallback {

    companion object {
        private const val ID_KEY = "uid"

        fun newInstance(uid: Int): SingleTripFragment {
            val bundle = Bundle().apply {
                putInt(ID_KEY, uid)
            }

            return SingleTripFragment().apply {
                arguments = bundle
            }
        }
    }

    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_single_trip_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.singleTripMap) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        GlobalScope.launch(Dispatchers.Main) {
            val uid = arguments!!.getInt(ID_KEY)
            val trip = withContext(Dispatchers.IO) {
                AppDatabase.get(requireContext())
                        .dao()
                        .loadById(uid)
            }

            map.apply {
                addPolyline(PolylineOptions().apply {
                    addAll(trip.points)
                })
                animateCamera(CameraUpdateFactory.newLatLngZoom(trip.points[0], UserLocation.ZOOM))
            }
        }
    }
}