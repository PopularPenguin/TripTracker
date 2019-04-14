package com.popularpenguin.triptracker.triplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ScreenNavigator
import kotlinx.android.synthetic.main.fragment_trip_list.*

class TripListFragment : Fragment() {

    companion object {
        fun newInstance(): TripListFragment {
            val fragment = TripListFragment()

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trip_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fab.setOnClickListener {
            ScreenNavigator(requireActivity().supportFragmentManager).loadMapTracker()
        }
    }
}