package com.popularpenguin.triptracker.singletrip.photoview

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PhotoPagerAdapter(fm: FragmentManager, private val uris: List<Uri>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = mutableListOf<Fragment>()

    init {
        for (uri in uris) {
            fragmentList.add(PhotoChildFragment.newInstance(uri))
        }
    }

    override fun getCount(): Int = uris.size

    override fun getItem(position: Int): Fragment = fragmentList[position]
}