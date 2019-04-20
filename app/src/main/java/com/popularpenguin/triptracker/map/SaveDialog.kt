package com.popularpenguin.triptracker.map

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.popularpenguin.triptracker.R

class SaveDialog(context: Context) {

    private val dialog = Dialog(context).apply {
        setContentView(R.layout.dialog_save)
    }

    private val descriptionEditText = dialog.findViewById<EditText>(R.id.dialogDescriptionEditText)
    private val cancelButton = dialog.findViewById<Button>(R.id.dialogCancelButton)
    private val saveButton = dialog.findViewById<Button>(R.id.dialogSaveButton)

    fun getDescription(): String {
        return descriptionEditText.text.toString()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun show() {
        dialog.show()
    }

    fun setCancelButtonOnClickListener(listener: ((View)-> Unit)) {
        cancelButton.setOnClickListener(listener)
    }

    fun setSaveButtonOnClickListener(listener: ((View) -> Unit)) {
        saveButton.setOnClickListener(listener)
    }
}