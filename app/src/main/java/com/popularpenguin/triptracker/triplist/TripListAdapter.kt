package com.popularpenguin.triptracker.triplist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.common.ImageLoader
import com.popularpenguin.triptracker.data.Trip
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
                val description = if (trip.description.length < 45) {
                    trip.description
                } else {
                    trip.description.take(45) + "..."
                }

                listDescriptionView.text = description
                listDateView.text = trip.getFormattedDate()
            }

            val photoUri = when {
                trip.captionPhoto != null -> trip.captionPhoto
                trip.uriList.isNotEmpty() -> trip.uriList[0]
                else -> null
            }

            if (photoUri != null) {
                ImageLoader.load(photoUri, itemView.listImageView, true)
            } else {
                itemView.listImageView.visibility = View.GONE
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