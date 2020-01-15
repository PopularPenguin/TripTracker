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
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.FileUtils
import com.popularpenguin.triptracker.common.PermissionValidator
import com.popularpenguin.triptracker.common.ScreenNavigator
import com.popularpenguin.triptracker.common.TripSnackbar
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.android.synthetic.main.fragment_trip_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.util.*

class TripListFragment : Fragment(), TripListAdapter.OnClick {

    companion object {
        fun newInstance(): TripListFragment {
            return TripListFragment()
        }
    }

    private val jobList = mutableListOf<Job>()

    private var startDate = 0L // Unix-time of earliest trip to load
    private var endDate = 0L // Unix-time of latest trip to load

    private lateinit var permissionValidator: PermissionValidator

    // TODO: Improve looks of dialog, add max available date
    private val showDatePickerListener = View.OnClickListener { view ->
        MaterialDialog(requireContext()).show {
            lifecycleOwner(this@TripListFragment)

            when (view.id) {
                R.id.startDateFab -> title(R.string.date_picker_title_start)
                R.id.endDateFab -> title(R.string.date_picker_title_end)
            }

            cornerRadius(10f)

            datePicker(maxDate = Calendar.getInstance()) { dialog, date ->
                when (view.id) {
                    R.id.startDateFab -> startDate = date.timeInMillis
                    R.id.endDateFab -> endDate = date.timeInMillis + 86_400_000L // add one day
                }

                if (startDate <= endDate) {
                    setRecyclerView(startDate, endDate)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trip_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        permissionValidator = PermissionValidator(requireActivity())

        setRecyclerView()

        showAllFab.setOnClickListener {
            setRecyclerView()
        }

        searchDescriptionFab.setOnClickListener {
            MaterialDialog(requireContext()).show {
                input(hintRes = R.string.dialog_search_hint, allowEmpty = true)
                message(R.string.dialog_search_message)
                cornerRadius(10f)
                negativeButton(R.string.dialog_search_cancel)
                positiveButton(R.string.dialog_search_search) { dialog ->
                    val searchText = dialog.getInputField()
                        .text
                        .toString()
                        .trim()

                    setRecyclerView(searchText = searchText)
                }
            }
        }

        startDateFab.setOnClickListener(showDatePickerListener)
        endDateFab.setOnClickListener(showDatePickerListener)

        newTripFab.setOnClickListener {
            if (permissionValidator.checkAllPermissions()) {
                ScreenNavigator(requireActivity().supportFragmentManager).loadMapTracker()
            } else {
                TripSnackbar(newTripFab, R.string.permissions_tracker, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_settings) {
                        startActivity(permissionValidator.settingsIntent)
                    }
                    .show()
            }
        }
    }

    private fun setRecyclerView(startDate: Long = 0L, endDate: Long = 0L, searchText: String = "") {
        val displayTripsJob = CoroutineScope(Main).launch {
            val tripList = withContext(IO) {
                val dao = AppDatabase.get(requireContext()).dao()

                if (startDate == 0L && endDate == 0L && searchText.isEmpty()) {
                    dao.getAll()
                } else if (searchText.isNotEmpty()) {
                    dao.loadByDescription(searchText)
                } else {
                    dao.loadByDate(startDate, endDate)
                }
            }
            val viewManager = GridLayoutManager(requireContext(), 2)
            val viewAdapter = TripListAdapter(tripList.toMutableList(), this@TripListFragment)

            requireActivity().findViewById<RecyclerView>(R.id.tripRecyclerView).apply {
                layoutManager = viewManager
                adapter = viewAdapter

                setHasFixedSize(true)

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    val fabList = listOf(
                        showAllFab,
                        searchDescriptionFab,
                        startDateFab,
                        endDateFab,
                        newTripFab
                    )

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

            newTripHintTextView.visibility = if (tripList.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        jobList.add(displayTripsJob)
    }

    override fun onClick(uid: Int) {
        ScreenNavigator(requireActivity().supportFragmentManager).loadSingleTrip(uid)
    }

    override fun onLongClick(adapter: TripListAdapter, position: Int, trip: Trip) {
        MaterialDialog(requireContext()).show {
            title(res = R.string.dialog_trip_delete_title)
            message(R.string.dialog_trip_delete_message)
            icon(R.drawable.ic_launcher_foreground)
            cornerRadius(10f)
            positiveButton(R.string.dialog_trip_delete_positive) {
                val deleteTripJob = CoroutineScope(IO).launch {
                    // delete all photo files associated with the trip
                    trip.uriList.forEach {
                        FileUtils.deletePhoto(requireContext(), it)
                    }

                    adapter.removeItem(position)

                    AppDatabase.get(requireContext())
                        .dao()
                        .delete(trip)
                }
                jobList.add(deleteTripJob)
            }
            negativeButton(R.string.dialog_trip_delete_negative)
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