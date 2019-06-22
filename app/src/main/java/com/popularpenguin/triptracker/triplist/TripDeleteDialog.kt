package com.popularpenguin.triptracker.triplist

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.FileUtils
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TripDeleteDialog(activity: Activity, adapter: TripListAdapter, position: Int, trip: Trip) {
    private val dialog = AlertDialog.Builder(activity, R.style.DialogTheme)
        .setTitle(R.string.dialog_trip_delete_title)
        .setMessage(R.string.dialog_trip_delete_message)
        .setPositiveButton(R.string.dialog_trip_delete_positive) { dialog, _ ->
            GlobalScope.launch(Dispatchers.IO) {
                // delete all photo files associated with the trip
                trip.uriList.forEach {
                    FileUtils.deletePhoto(activity, it)
                }

                adapter.removeItem(position)

                AppDatabase.get(activity)
                    .dao()
                    .delete(trip)
            }

            dialog.dismiss()
        }
        .setNegativeButton(R.string.dialog_trip_delete_negative) { dialog, _ ->
            dialog.dismiss()
        }
        .setIcon(R.drawable.ic_launcher_foreground)

    fun show() {
        dialog.show()
    }
}