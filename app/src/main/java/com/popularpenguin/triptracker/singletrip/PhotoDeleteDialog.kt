package com.popularpenguin.triptracker.singletrip

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.popularpenguin.triptracker.R

class PhotoDeleteDialog(context: Context) {

    private val dialog = AlertDialog.Builder(context, R.style.DialogTheme)
        .setTitle(R.string.dialog_photo_delete_title)
        .setMessage(R.string.dialog_photo_delete_message)
        .setIcon(R.drawable.ic_launcher_foreground) // TODO: Change to app icon
        .setNegativeButton(R.string.dialog_photo_delete_negative) { dialog, _ ->
            dialog.cancel()
        }
        .setPositiveButton(R.string.dialog_photo_delete_positive) { dialog, _ ->
            dialog.dismiss()
        }
        .create()

    fun setOnDismissListener(listener: ((DialogInterface) -> Unit)) {
        dialog.setOnDismissListener(listener)
    }

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