package com.popularpenguin.triptracker.common

import android.app.Dialog
import android.content.Context
import android.widget.Button
import com.popularpenguin.triptracker.R

class AboutDialog(context: Context) {

    private val dialog = Dialog(context).apply {
        setContentView(R.layout.dialog_about)

        findViewById<Button>(R.id.dialogAboutOkButton).apply {
            setOnClickListener {
                dismiss()
            }
        }
    }

    fun show() {
        dialog.show()
    }
}