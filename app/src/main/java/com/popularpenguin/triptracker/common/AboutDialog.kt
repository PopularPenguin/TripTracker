package com.popularpenguin.triptracker.common

import android.app.Dialog
import android.content.Context
import android.widget.Button
import com.popularpenguin.triptracker.R

class AboutDialog(context: Context) {

    private val dialog = Dialog(context).apply {
        setContentView(R.layout.dialog_about)
    }

    private val okButton = dialog.findViewById<Button>(R.id.dialogAboutOkButton).apply {
        setOnClickListener {
            dialog.dismiss()
        }
    }

    fun show() {
        dialog.show()
    }
}