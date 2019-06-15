package com.popularpenguin.triptracker.singletrip

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ImageLoader
import com.popularpenguin.triptracker.data.Trip
import kotlinx.android.synthetic.main.photo_list_item.view.*

class PhotoAdapter(private val trip: Trip, private val handler: OnClick) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    interface OnClick {
        fun onClick(photoUri: Uri)
        fun onLongClick(adapter: PhotoAdapter, position: Int, trip: Trip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.photo_list_item, parent, false)

        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoUri = trip.uriList[position]
        holder.bind(photoUri)
    }

    override fun getItemCount(): Int = trip.uriList.size

    fun removeItem(position: Int) {
        notifyItemRemoved(position)

        trip.photoMarkerList.removeAt(position)
        trip.uriList.removeAt(position)
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener,
            View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(photoUri: Uri) {
            ImageLoader.load(photoUri, itemView.singleTripPhotoView, center = true)
        }

        override fun onClick(view: View) {
            handler.onClick(trip.uriList[adapterPosition])
        }

        override fun onLongClick(view: View): Boolean {
            handler.onLongClick(
                    this@PhotoAdapter,
                    adapterPosition,
                    trip
            )

            return true
        }
    }
}