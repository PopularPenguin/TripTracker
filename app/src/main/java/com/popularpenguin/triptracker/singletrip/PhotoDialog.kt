package com.popularpenguin.triptracker.singletrip

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.Window
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ImageLoader
import com.popularpenguin.triptracker.common.TripSnackbar
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.android.synthetic.main.dialog_display_photo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoDialog(context: Context, private val trip: Trip, private val photoUri: Uri) {

    private val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen).apply {
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutInflater.inflate(R.layout.dialog_display_photo, null))

        setCaptionPhotoFab.setOnClickListener {
            saveCaption(context)
            (it as FloatingActionButton).hide()
        }
    }

    private val photoView = dialog.dialogPhotoView.apply {
        setOnClickListener {
            dialog.dismiss()
        }
    }

    fun show() {
        ImageLoader.load(photoUri, photoView)

        dialog.show()
    }

    private fun saveCaption(context: Context) {
        val dao = AppDatabase.get(context).dao()
        val previousCaptionPhoto = trip.captionPhoto

        GlobalScope.launch(Dispatchers.IO) {
            trip.captionPhoto = photoUri
            dao.update(trip)
        }

        TripSnackbar(photoView, R.string.snackbar_photo_caption, Snackbar.LENGTH_LONG)
            .setAction(R.string.snackbar_undo) {
                trip.captionPhoto = previousCaptionPhoto
                dialog.setCaptionPhotoFab.show()
                GlobalScope.launch(Dispatchers.IO) {
                    dao.update(trip)
                }
            }.show()
    }
}