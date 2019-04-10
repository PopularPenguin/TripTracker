package com.popularpenguin.triptracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.popularpenguin.triptracker.map.MapFragment
import com.popularpenguin.triptracker.trips.TripFragment

class ScreenNavigator(private val fm: FragmentManager) {

    fun loadMap() {
        fm.beginTransaction()
            .replace(R.id.fragment_container, MapFragment.newInstance() as Fragment)
            .addToBackStack(null)
            .commit()
    }

    fun loadTrips() {
        fm.beginTransaction()
            .replace(R.id.fragment_container, TripFragment.newInstance())
            .commit()
    }
}