package com.popularpenguin.triptracker.singletrip.photoview

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_child_photo.view.*

class PhotoChildFragment : Fragment() {

    companion object {
        private const val PHOTO_URI_KEY = "uri"

        fun newInstance(photoUri: Uri): PhotoChildFragment {
            val bundle = Bundle().apply {
                putString(PHOTO_URI_KEY, photoUri.toString())
            }

            return PhotoChildFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_child_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val photoUri = Uri.parse(arguments!!.getString(PHOTO_URI_KEY))

        ImageLoader.load(photoUri, view.photoView)
    }
}