package com.popularpenguin.triptracker.singletrip.photoview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

// Necessary class to fix index out of bounds crash when zooming photoView inside a ViewPager
class PhotoViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

        return false
    }
}