package com.popularpenguin.triptracker.triplist

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.FileUtils
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TripDeleteDialog(context: Context, adapter: TripListAdapter, position: Int, trip: Trip) {

    val jobList = mutableListOf<Job>()

    private val dialog = Dialog(context).apply {
        setContentView(R.layout.dialog_trip_delete)

        findViewById<Button>(R.id.dialogTripCancelButton).apply {
            setOnClickListener {
                cancel()
            }
        }
        findViewById<Button>(R.id.dialogTripDeleteButton).apply {
            setOnClickListener {
                delete(context, adapter, position, trip)
                dismiss()
            }
        }
    }

    fun show() {
        dialog.show()
    }

    private fun delete(context: Context, adapter: TripListAdapter, position: Int, trip: Trip) {
        val deleteTripJob = GlobalScope.launch(Dispatchers.IO) {
            // delete all photo files associated with the trip
            trip.uriList.forEach {
                FileUtils.deletePhoto(context, it)
            }

            adapter.removeItem(position)

            AppDatabase.get(context)
                .dao()
                .delete(trip)
        }
        jobList.add(deleteTripJob)
    }
}