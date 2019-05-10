package com.popularpenguin.triptracker.common

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.popularpenguin.triptracker.R

class TripSnackbar(view: View, resId: Int, duration: Int) {

    private val snackbar = Snackbar.make(view, resId, duration).apply {
            val snackbarView = this.view as Snackbar.SnackbarLayout
            val layout = snackbarView.getChildAt(0) as LinearLayout
            val textView = layout.getChildAt(0) as TextView
            val actionButton = layout.getChildAt(1) as Button

            snackbarView.background = context.getDrawable(R.drawable.gradient_list_view)
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            with(actionButton) {
                setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
                setTextColor(ContextCompat.getColor(context, R.color.white))
                typeface = Typeface.DEFAULT_BOLD
            }
        }

    fun setAction(resId: Int, listener: (View) -> Unit): TripSnackbar {
        snackbar.setAction(resId, listener)

        return this
    }

    fun show() {
        snackbar.show()
    }
}