package com.popularpenguin.triptracker.triplist

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.popularpenguin.triptracker.R

class SearchDialog(context: Context) {

    private val dialog = Dialog(context).apply {
        setContentView(R.layout.dialog_search)
    }

    private val descriptionEditText = dialog.findViewById<EditText>(R.id.dialogSearchEditText)
    private val cancelButton = dialog.findViewById<Button>(R.id.dialogSearchCancelButton).apply {
        setOnClickListener {
            dialog.dismiss()
        }
    }
    private val searchButton = dialog.findViewById<Button>(R.id.dialogSearchButton)

    fun getSearchString(): String {
        return descriptionEditText.text
                .toString()
                .trim()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun show() {
        dialog.show()
    }

    fun setSearchButtonOnClickListener(listener: ((View) -> Unit)) {
        searchButton.setOnClickListener(listener)
    }
}