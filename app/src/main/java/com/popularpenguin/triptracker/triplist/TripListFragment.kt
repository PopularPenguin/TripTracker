package com.popularpenguin.triptracker.triplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ScreenNavigator
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.android.synthetic.main.fragment_trip_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TripListFragment : Fragment(), TripListAdapter.OnClick {

    companion object {
        fun newInstance(): TripListFragment {
            return TripListFragment()
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

            val viewManager = GridLayoutManager(requireContext(), 2)
            val viewAdapter = TripListAdapter(tripList.toMutableList(), this@TripListFragment)

            requireActivity().findViewById<RecyclerView>(R.id.tripRecyclerView).apply {
                setHasFixedSize(true)

                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    override fun onClick(uid: Int) {
        ScreenNavigator(requireActivity().supportFragmentManager).loadSingleTrip(uid)
    }

    override fun onLongClick(adapter: TripListAdapter, position: Int, trip: Trip) {
        AlertDialog.Builder(requireContext(), R.style.DialogTheme)
            .setTitle(R.string.dialog_delete_title)
            .setMessage(R.string.dialog_delete_message)
            .setPositiveButton(R.string.dialog_delete_positive) { dialog, _ ->
                GlobalScope.launch(Dispatchers.IO) {
                    adapter.removeItem(position)
                    AppDatabase.get(requireContext()).dao().delete(trip)
                }

                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_delete_negative) { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(R.drawable.ic_launcher_foreground) // TODO: Change to app icon
            .show()
    }
}