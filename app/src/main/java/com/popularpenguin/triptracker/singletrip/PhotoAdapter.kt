package com.popularpenguin.triptracker.singletrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.data.Trip

class PhotoAdapter(private val trip: Trip, private val handler: OnClick) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    interface OnClick {
        fun onClick(photoPath: String)
        fun onLongClick(adapter: PhotoAdapter, position: Int, photoPath: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.photo_list_item, parent, false)

        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoPath = trip.photoList[position]
        holder.bind(photoPath)
    }

    override fun getItemCount(): Int = trip.photoList.size

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
        trip.photoList.removeAt(position)

        // TODO: Remove photo from gallery
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener,
            View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(photoPath: String) {
            // TODO: Use Picasso to put the photo into the ImageView
        }

        override fun onClick(view: View) {
            // Display the photo clicked in full screen
            // ...

            handler.onClick(trip.photoList[adapterPosition])
        }

        override fun onLongClick(view: View): Boolean {
            // Delete the photo? Could delete this method
            handler.onLongClick(
                    this@PhotoAdapter,
                    adapterPosition,
                    trip.photoList[adapterPosition]
            )

            return true
        }
    }
}