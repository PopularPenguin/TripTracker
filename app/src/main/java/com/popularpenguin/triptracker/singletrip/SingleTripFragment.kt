package com.popularpenguin.triptracker.singletrip

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

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
        val photoRecyclerView = requireActivity().findViewById<RecyclerView>(R.id.photoRecyclerView)

        if (trip.uriList.isEmpty()) {
            photoRecyclerView.visibility = View.GONE // no photos to display, so we don't need a recycler view for them

            return
        }

        val viewManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val viewAdapter = PhotoAdapter(trip, this)

        photoRecyclerView.apply {
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

        val loadTripJob = CoroutineScope(Main).launch {
            val uid = arguments!!.getInt(ID_KEY)
            trip = withContext(IO) {
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
                // TODO: Fix bug here; clicking photo marker returns last index for all markers
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

                if (trip.uriList.isNotEmpty()) {
                    // hide the view when the camera moves
                    setOnCameraMoveStartedListener {
                        ViewAnimationUtils.hidePhotoView(photoRecyclerView)
                    }
                    // show the view when the camera isn't moving
                    setOnCameraIdleListener {
                        ViewAnimationUtils.showPhotoView(photoRecyclerView)
                    }
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
        // TODO: Test index
        Log.d(javaClass.simpleName, "Position is currently $position")
        ScreenNavigator(requireFragmentManager()).loadPhotoPager(trip.uid, position)
    }

    override fun onLongClick(adapter: PhotoAdapter, position: Int, trip: Trip) {
        MaterialDialog(requireContext()).show {
            lifecycleOwner(this@SingleTripFragment)

            title(R.string.dialog_photo_delete_title)
            message(R.string.dialog_photo_delete_message)
            cornerRadius(10f)
            negativeButton(R.string.dialog_photo_delete_negative)
            positiveButton(R.string.dialog_photo_delete_positive) {
                val photoUri = trip.uriList[position]
                val key = photoUri.toString()
                val marker = photoMarkerMap[key]

                marker?.remove()
                photoMarkerMap.remove(key)

                val deleteJob = CoroutineScope(IO).launch {
                    FileUtils.deletePhoto(requireContext(), photoUri)

                    adapter.removeItem(position)

                    AppDatabase.get(requireContext())
                        .dao()
                        .update(trip)
                }
                jobList.add(deleteJob)
            }
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