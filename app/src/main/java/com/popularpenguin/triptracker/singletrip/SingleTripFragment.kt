package com.popularpenguin.triptracker.singletrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.PolylineOptions
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.map.UserLocation
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.android.synthetic.main.fragment_single_trip_map.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleTripFragment: Fragment(), OnMapReadyCallback, PhotoAdapter.OnClick {

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
    private lateinit var trip: Trip

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_single_trip_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.singleTripMap) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    private fun setRecyclerView(trip: Trip) {
        val viewManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        )
        val viewAdapter = PhotoAdapter(trip, this)

        requireActivity().findViewById<RecyclerView>(R.id.photoRecyclerView).apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        GlobalScope.launch(Dispatchers.Main) {
            val uid = arguments!!.getInt(ID_KEY)
            trip = withContext(Dispatchers.IO) {
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

            singleTripZoomFab.setOnClickListener {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(trip.points[0], UserLocation.ZOOM))
            }

            setRecyclerView(trip)
        }
    }

    override fun onClick(photoPath: String) {
        PhotoDialog(requireContext(), photoPath).show()
    }

    override fun onLongClick(adapter: PhotoAdapter, position: Int, photoPath: String) {
        // TODO: Display dialog to delete photo from gallery
    }
}