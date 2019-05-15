package com.popularpenguin.triptracker.triplist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.data.Trip
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.trip_list_item.view.*

class TripListAdapter(private val tripList: MutableList<Trip>, private val handler: OnClick) :
    RecyclerView.Adapter<TripListAdapter.TripViewHolder>() {

    interface OnClick {
        fun onClick(uid: Int)
        fun onLongClick(adapter: TripListAdapter, position: Int, trip: Trip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.trip_list_item, parent, false)

        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = tripList[position]
        holder.bind(trip)
    }

    override fun getItemCount(): Int = tripList.size

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
        tripList.removeAt(position)
    }

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener,
            View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(trip: Trip) {
            with (itemView) {
                val description = trip.description
                    .take(45) +
                        "..."

                listDescriptionView.text = description
                listDateView.text = trip.getFormattedDate()
            }

            val photo = when {
                trip.captionPhoto.isNotEmpty() -> trip.captionPhoto
                trip.photoList.isNotEmpty() -> trip.photoList[0]
                else -> ""
            }

            if (photo.isNotEmpty()) {
                Picasso.get()
                    .load(photo)
                    .fit()
                    .into(itemView.listImageView)
            }
        }

        override fun onClick(view: View) {
            handler.onClick(tripList[adapterPosition].uid)
        }

        override fun onLongClick(view: View): Boolean {
            handler.onLongClick(
                    this@TripListAdapter,
                    adapterPosition,
                    tripList[adapterPosition]
            )

            return true
        }
    }
}