package com.popularpenguin.triptracker.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.map.MapTrackerFragment
import com.popularpenguin.triptracker.singletrip.SingleTripFragment
import com.popularpenguin.triptracker.triplist.TripListFragment

class ScreenNavigator(private val fm: FragmentManager) {

    enum class Screen {
        MapTracker,
        SingleTrip,
        TripList
    }

    var screen = Screen.TripList
    private set

    init {
        fm.addOnBackStackChangedListener {
            if (fm.findFragmentById(R.id.fragment_container) is TripListFragment) {
                screen = Screen.TripList
            }
        }
    }

    fun loadMapTracker() {
        screen = Screen.MapTracker
        fm.beginTransaction()
            .replace(R.id.fragment_container, MapTrackerFragment.newInstance() as Fragment)
            .addToBackStack(null)
            .commit()
    }

    fun loadSingleTrip(uid: Int) {
        screen = Screen.SingleTrip
        fm.beginTransaction()
                .replace(R.id.fragment_container, SingleTripFragment.newInstance(uid))
                .addToBackStack(null)
                .commit()
    }

    fun loadTripList() {
        screen = Screen.TripList
        fm.beginTransaction()
            .replace(R.id.fragment_container, TripListFragment.newInstance())
            .commit()
        fm.popBackStack()
    }
}