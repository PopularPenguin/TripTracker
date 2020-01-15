package com.popularpenguin.triptracker.common

import android.view.View
import android.view.animation.TranslateAnimation

object ViewAnimationUtils {

    // Photo RecyclerView shown in SingleTripFragment
    fun hidePhotoView(view: View) {
        // Slide down off the screen
        val hidePhotosAnimation = TranslateAnimation(
            0.0f,
            0.0f,
            0.0f,
            view.height.toFloat()
        ).apply {
            duration = 200
            fillAfter = true
        }

        view.startAnimation(hidePhotosAnimation)
    }

    fun showPhotoView(view: View) {
        // Slide up into the screen
        val showPhotosAnimation = TranslateAnimation(
            0.0f,
            0.0f,
            view.height.toFloat(),
            0.0f
        ).apply {
            duration = 200
            fillAfter = true
        }

        view.startAnimation(showPhotosAnimation)
    }
}