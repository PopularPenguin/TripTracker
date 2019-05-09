package com.popularpenguin.triptracker.singletrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.SphericalUtil
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
                // Add the polylines for the entire trip
                addPolyline(PolylineOptions().apply {
                    addAll(trip.points)
                })
                // Add the trip's starting point
                addMarker(MarkerOptions()
                    .position(trip.points.first())
                    .title(getString(R.string.marker_start))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
                // Add the trip's end point if there is more than 0.1 miles between the start and end points
                val distanceBetweenStartAndEnd = SphericalUtil.computeDistanceBetween(
                    trip.points.first(),
                    trip.points.last()
                ) * 0.000621371 // meters to miles

                if (distanceBetweenStartAndEnd > 0.1 /* miles */) {
                    addMarker(
                        MarkerOptions()
                            .position(trip.points.last())
                            .title(getString(R.string.marker_end))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )
                }

                animateCamera(CameraUpdateFactory.newLatLngZoom(trip.points.first(), UserLocation.ZOOM))

                // hide the distance view when the camera moves
                setOnCameraMoveStartedListener {
                    val hideAnimation = TranslateAnimation(
                            0.0f,
                            -singleTripDistanceTextView.width.toFloat(),
                            singleTripDistanceTextView.height.toFloat(),
                            -singleTripDistanceTextView.height.toFloat()
                    ).apply {
                        duration = 500
                        fillAfter = true
                    }

                    singleTripDistanceTextView.startAnimation(hideAnimation)
                }
                // show the distance view when the camera isn't moving
                setOnCameraIdleListener {
                    val showAnimation = TranslateAnimation(
                            -singleTripDistanceTextView.width.toFloat(),
                            0.0f,
                            0.0f,
                            singleTripDistanceTextView.height.toFloat()
                    ).apply {
                        duration = 500
                        fillAfter = true
                    }

                    singleTripDistanceTextView.startAnimation(showAnimation)
                }
            }

            singleTripZoomFab.setOnClickListener {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(trip.points.first(), UserLocation.ZOOM))
            }

            setRecyclerView(trip)

            singleTripDistanceTextView.append(
                    "${trip.totalDistance.toString().take(6)} ${getString(R.string.text_distance_units)}"
            )
        }
    }

    override fun onClick(photoPath: String) {
        // TODO: Change dialog to a Fragment with a FragmentStatePagerAdapter to thumb through photos
        PhotoDialog(requireContext(), photoPath).show()
    }

    override fun onLongClick(adapter: PhotoAdapter, position: Int, trip: Trip) {
        PhotoDeleteDialog(requireContext(), adapter, position, trip, trip.uriList[position]).show()
    }
}