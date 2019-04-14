package com.popularpenguin.triptracker.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.popularpenguin.triptracker.R

class MapTrackerFragment: Fragment() {

    companion object {
        fun newInstance(): MapTrackerFragment {
            val fragment = MapTrackerFragment()

            return fragment
        }
    }

    private lateinit var tripTracker: TripTracker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tripTracker = TripTracker(this)
    }

    override fun onResume() {
        super.onResume()

        tripTracker.onResume()
    }

    override fun onPause() {
        tripTracker.onPause()

        super.onPause()
    }
}