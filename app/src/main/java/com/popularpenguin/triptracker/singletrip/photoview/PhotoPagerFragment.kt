package com.popularpenguin.triptracker.singletrip.photoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.TripSnackbar
import com.popularpenguin.triptracker.data.Trip
import com.popularpenguin.triptracker.room.AppDatabase
import kotlinx.android.synthetic.main.fragment_photo_pager.*
import kotlinx.android.synthetic.main.fragment_photo_pager.view.*
import kotlinx.coroutines.*

class PhotoPagerFragment: Fragment() {

    companion object {
        private const val ID_KEY = "uid"
        private const val PHOTO_URI_KEY = "uri"

        fun newInstance(uid: Int, photoPosition: Int): PhotoPagerFragment {
            val bundle = Bundle().apply {
                putInt(ID_KEY, uid)
                putInt(PHOTO_URI_KEY, photoPosition)
            }

            return PhotoPagerFragment().apply {
                arguments = bundle
            }
        }
    }

    private val jobList = mutableListOf<Job>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_photo_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GlobalScope.launch(Dispatchers.Main) {
            val uid = arguments!!.getInt(ID_KEY)
            val position = arguments!!.getInt(PHOTO_URI_KEY)
            val trip = withContext(Dispatchers.IO) {
                AppDatabase.get(requireContext())
                    .dao()
                    .loadById(uid)
            }

            photoViewPager.adapter = PhotoPagerAdapter(requireFragmentManager(), trip.uriList)
            photoViewPager.currentItem = position

            view.setCaptionPhotoFab.setOnClickListener {
                saveCaption(view, trip, position)
            }
        }
    }

    private fun saveCaption(parentView: View, trip: Trip, position: Int) {
        val dao = AppDatabase.get(requireContext())
            .dao()
        val previousCaptionPhoto = trip.captionPhoto

        val updateJob = GlobalScope.launch(Dispatchers.IO) {
            trip.captionPhoto = trip.uriList[position]
            dao.update(trip)
        }
        jobList.add(updateJob)

        TripSnackbar(parentView, R.string.snackbar_photo_caption, Snackbar.LENGTH_LONG)
            .setAction(R.string.snackbar_undo) {
                trip.captionPhoto = previousCaptionPhoto
                GlobalScope.launch(Dispatchers.IO) {
                    dao.update(trip)
                }
            }.show()
    }

    override fun onDestroy() {
        for (job in jobList) {
            if (job.isActive) {
                job.cancel()
            }
        }

        super.onDestroy()
    }
}