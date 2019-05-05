package com.popularpenguin.triptracker.triplist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.*

class TripListFragment : Fragment(), TripListAdapter.OnClick {

    companion object {
        fun newInstance(): TripListFragment {
            return TripListFragment()
        }
    }

    private var startDate = 0L // Unix-time of earliest trip to load
    private var endDate = 0L // Unix-time of latest trip to load

    // TODO: Refactor into a separate class?
    private val showDatePickerListener = View.OnClickListener {
        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            when (it.id) {
                R.id.startDateFab -> startDate = GregorianCalendar(year, month, day).timeInMillis
                R.id.endDateFab -> endDate = GregorianCalendar(year, month, day + 1).timeInMillis
            }

            if (startDate <= endDate) {
                setRecyclerView(startDate, endDate)
            }
        }
        val currentDate = Date()

        DatePickerDialog(
                requireContext(),
                AlertDialog.THEME_DEVICE_DEFAULT_DARK, // TODO: Better custom theme?
                dateSetListener,
                currentDate.year + 1900,
                currentDate.month,
                currentDate.day
        ).apply {
            when (it.id) {
                R.id.startDateFab -> setTitle(R.string.date_picker_title_start)
                R.id.endDateFab -> setTitle(R.string.date_picker_title_end)
            }
            datePicker.minDate = GregorianCalendar(2019, 0, 1).timeInMillis
            datePicker.maxDate = System.currentTimeMillis()
            updateDate(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        }.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trip_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView(startDate, endDate)

        startDateFab.setOnClickListener(showDatePickerListener)
        endDateFab.setOnClickListener(showDatePickerListener)

        newTripFab.setOnClickListener {
            ScreenNavigator(requireActivity().supportFragmentManager).loadMapTracker()
        }
    }

    private fun setRecyclerView(startDate: Long, endDate: Long) {
        GlobalScope.launch(Dispatchers.Main) {
            val tripList = withContext(Dispatchers.IO) {
                if (startDate == 0L && endDate == 0L) {
                    AppDatabase.get(requireContext())
                            .dao()
                            .getAll()
                } else {
                    AppDatabase.get(requireContext())
                            .dao()
                            .loadByDate(startDate, endDate)
                }
            }
            val viewManager = GridLayoutManager(requireContext(), 2)
            val viewAdapter = TripListAdapter(tripList.toMutableList(), this@TripListFragment)

            requireActivity().findViewById<RecyclerView>(R.id.tripRecyclerView).apply {
                layoutManager = viewManager
                adapter = viewAdapter

                setHasFixedSize(true)

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    val fabList = listOf(startDateFab, endDateFab, newTripFab)

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        fabList.forEach {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                it.show()
                            } else {
                                it.hide()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onClick(uid: Int) {
        ScreenNavigator(requireActivity().supportFragmentManager).loadSingleTrip(uid)
    }

    override fun onLongClick(adapter: TripListAdapter, position: Int, trip: Trip) {
        TripDeleteDialog(requireContext(), adapter, position, trip).show()
    }
}