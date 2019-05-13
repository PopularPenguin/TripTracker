package com.popularpenguin.triptracker.singletrip

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoDeleteDialog(context: Context, adapter: PhotoAdapter, position: Int, trip: Trip, photoUri: Uri) {

    private val dialog = AlertDialog.Builder(context, R.style.DialogTheme)
        .setTitle(R.string.dialog_photo_delete_title)
        .setMessage(R.string.dialog_photo_delete_message)
        .setIcon(R.drawable.ic_launcher_foreground) // TODO: Change to app icon
        .setNegativeButton(R.string.dialog_photo_delete_negative) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(R.string.dialog_photo_delete_positive) { dialog, _ ->
            GlobalScope.launch(Dispatchers.IO) {
                context.contentResolver.delete(photoUri, null, null)
                adapter.removeItem(position)
                AppDatabase.get(context)
                    .dao()
                    .update(trip)
            }

            photoDeleted = true
            dialog.dismiss()
        }
        .create()

    var photoDeleted = false

    fun show() {
        dialog.apply {
            setOnShowListener {
                (it as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(context, R.color.red))
            }

            show()
        }
    }
}