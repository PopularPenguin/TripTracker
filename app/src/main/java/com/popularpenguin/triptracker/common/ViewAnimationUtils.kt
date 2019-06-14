package com.popularpenguin.triptracker.common

import android.view.View
import android.view.animation.TranslateAnimation

object ViewAnimationUtils {

    // Info view displayed in the top-left corner of the screen
    fun hideInfoView(view: View) {
        // Slide left and up off the screen
        val hideAnimation = TranslateAnimation(
            0.0f,
            -view.width.toFloat(),
            view.height.toFloat(),
            -view.height.toFloat()
        ).apply {
            duration = 200
            fillAfter = true
        }

        view.startAnimation(hideAnimation)
    }

    fun showInfoView(view: View) {
        // Slide right and down into screen
        val showAnimation = TranslateAnimation(
            -view.width.toFloat(),
            0.0f,
            0.0f,
            view.height.toFloat()
        ).apply {
            duration = 200
            fillAfter = true
        }

        view.startAnimation(showAnimation)
    }

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