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
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import com.google.maps.android.ui.IconGenerator
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.FileUtils
import com.popularpenguin.triptracker.common.ScreenNavigator
import com.popularpenguin.triptracker.common.ViewAnimationUtils
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.map.UserLocation
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.android.synthetic.main.fragment_single_trip_map.*
import kotlinx.coroutines.*

class SingleTripFragment : Fragment(), OnMapReadyCallback, PhotoAdapter.OnClick {

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

    private var jobList = mutableListOf<Job>()
    private var photoMarkerMap = mutableMapOf<String, Marker>()

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
        map = googleMap.apply {
            mapType = GoogleMap.MAP_TYPE_TERRAIN
            uiSettings.isMapToolbarEnabled = false
        }

        val loadTripJob = GlobalScope.launch(Dispatchers.Main) {
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
                addMarker(
                    MarkerOptions()
                        .position(trip.points.first())
                        .title(getString(R.string.marker_start))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .zIndex(1.0f)
                )

                // Add the trip's end point if there is more than 0.01 miles between the start and end points
                val distanceBetweenStartAndEnd = SphericalUtil.computeDistanceBetween(
                    trip.points.first(),
                    trip.points.last()
                ) * 0.000621371 // meters to miles

                if (distanceBetweenStartAndEnd > 0.01 /* miles */) {
                    addMarker(
                        MarkerOptions()
                            .position(trip.points.last())
                            .title(getString(R.string.marker_end))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .zIndex(1.0f)
                    )
                }

                // Add markers for the location of each photo taken
                if (trip.uriList.isNotEmpty()) {
                    for ((index, latLng) in trip.photoMarkerList.withIndex()) {
                        val iconBitmap = IconGenerator(requireContext()).apply {
                            setBackground(resources.getDrawable(R.drawable.photo, null))
                        }.makeIcon()

                        val marker = addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap))
                        ).apply {
                            tag = trip.uriList[index]
                            setOnMarkerClickListener {
                                if (it.tag != null) {
                                    onClick(index)
                                }

                                false
                            }
                        }

                        photoMarkerMap["${trip.uriList[index]}"] = marker
                    }
                }

                val latLngBuilder = LatLngBounds.Builder()

                trip.points.forEach { latLngBuilder.include(it) }
                moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBuilder.build(), 300 /* padding */))

                // hide the distance view when the camera moves
                setOnCameraMoveStartedListener {
                    ViewAnimationUtils.hideInfoView(singleTripDistanceTextView)
                    ViewAnimationUtils.hidePhotoView(photoRecyclerView)
                }
                // show the distance view when the camera isn't moving
                setOnCameraIdleListener {
                    ViewAnimationUtils.showInfoView(singleTripDistanceTextView)
                    ViewAnimationUtils.showPhotoView(photoRecyclerView)
                }
            }

            singleTripZoomFab.setOnClickListener {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(trip.points.first(), UserLocation.ZOOM))
            }

            singleTripMapTypeFab.setOnClickListener {
                if (map.mapType == GoogleMap.MAP_TYPE_TERRAIN) {
                    map.mapType = GoogleMap.MAP_TYPE_HYBRID
                } else {
                    map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }
            }

            setRecyclerView(trip)

            singleTripDistanceTextView.append(
                "${trip.totalDistance.toString().take(6)} ${getString(R.string.text_distance_units)}"
            )
        }

        jobList.add(loadTripJob)
    }

    override fun onClick(position: Int) {
        ScreenNavigator(requireFragmentManager()).loadPhotoPager(trip.uid, position)
    }

    override fun onLongClick(adapter: PhotoAdapter, position: Int, trip: Trip) {
        PhotoDeleteDialog(requireContext()).apply {
            setOnDeleteListener { _, _ ->
                val photoUri = trip.uriList[position]
                val key = photoUri.toString()
                val marker = photoMarkerMap[key]

                marker?.remove()
                photoMarkerMap.remove(key)

                val deleteJob = GlobalScope.launch(Dispatchers.IO) {
                    FileUtils.deletePhoto(requireContext(), photoUri)

                    adapter.removeItem(position)

                    AppDatabase.get(requireContext())
                        .dao()
                        .update(trip)
                }
                jobList.add(deleteJob)
            }

            show()
        }
    }

    override fun onDestroy() {
        for (job in jobList) {
            if (job.isActive) {
                job.cancel()
            }
        }

        super.onDestroy()
    }
}