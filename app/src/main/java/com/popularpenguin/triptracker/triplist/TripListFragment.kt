package com.popularpenguin.triptracker.triplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ScreenNavigator
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.android.synthetic.main.fragment_trip_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TripListFragment : Fragment(), TripListAdapter.OnClick {

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

        setRecyclerView()
    }

    private fun setRecyclerView() {
        GlobalScope.launch(Dispatchers.Main) {
            val tripList = withContext(Dispatchers.IO) {
                AppDatabase.get(requireContext()).dao().getAll()
            }

            val viewManager = LinearLayoutManager(requireContext())
            val viewAdapter = TripListAdapter(tripList, this@TripListFragment)

            val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.tripRecyclerView).apply {
                setHasFixedSize(true)

                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    override fun onClick(position: Int) {
        Toast.makeText(requireContext(), "Position = $position", Toast.LENGTH_LONG).show()
    }
}