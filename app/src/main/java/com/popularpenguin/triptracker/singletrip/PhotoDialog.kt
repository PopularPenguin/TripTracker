package com.popularpenguin.triptracker.singletrip

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.popularpenguin.triptracker.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_display_photo.*

// TODO: Insert a FragmentStatePagerAdapter into this view or create a new fragment to swap to..
class PhotoDialog(context: Context, private val photoPath: String) {

    private val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen).apply {
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutInflater.inflate(R.layout.dialog_display_photo, null))
    }

    private val photoView = dialog.dialogPhotoView.apply {
        setOnClickListener {
            dialog.dismiss()
        }
    }

    fun show() {
        Picasso.get()
            .load(photoPath)
            .into(photoView)

        dialog.show()
    }
}