package com.popularpenguin.triptracker.triplist

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.popularpenguin.triptracker.R

class TripDeleteDialog(context: Context) {

    private val dialogBuilder = AlertDialog.Builder(context, R.style.DialogTheme)
        .setTitle(R.string.dialog_trip_delete_title)
        .setMessage(R.string.dialog_trip_delete_message)
        .setIcon(R.drawable.ic_launcher_foreground)
        .setNegativeButton(R.string.dialog_trip_delete_negative) { dialog, _ ->
            dialog.cancel()
        }

    fun setOnDeleteListener(listener: ((DialogInterface, Int) -> Unit)) {
        dialogBuilder.setPositiveButton(R.string.dialog_trip_delete_positive, listener)
    }

    fun show() {
        val dialog = dialogBuilder.create()

        dialog.apply {
            setOnShowListener {
                (it as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(context, R.color.red))
            }

            show()
        }
    }
}