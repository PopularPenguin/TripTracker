package com.popularpenguin.triptracker.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.popularpenguin.triptracker.R
import kotlinx.android.synthetic.main.fragment_map_tracker.*

class MapTrackerFragment: Fragment() {

    companion object {
        fun newInstance(): MapTrackerFragment {
            return MapTrackerFragment()
        }
    }

    private lateinit var tripTracker: TripTracker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tripTracker = TripTracker(this).apply {
            addCameraListener(trackerCameraFab)
            addControlListener(trackerControlFab, trackerControlHintTextView)
            addZoomListener(trackerZoomFab)
            addMapTypeListener(trackerMapTypeFab)
            setInfoTextView(trackerDistanceTextView)
        }
    }

    override fun onResume() {
        super.onResume()

        tripTracker.onResume()
    }

    override fun onDestroy() {
        tripTracker.onDestroy()

        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TripTracker.REQUEST_PHOTO && resultCode == Activity.RESULT_OK) {
            tripTracker.storePhoto()
        }
    }
}