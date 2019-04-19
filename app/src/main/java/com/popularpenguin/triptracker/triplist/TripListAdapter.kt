package com.popularpenguin.triptracker.triplist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import com.popularpenguin.triptracker.data.Trip
import kotlinx.android.synthetic.main.trip_list_item.view.*

class TripListAdapter(private val tripList: List<Trip>, private val handler: OnClick) :
    RecyclerView.Adapter<TripListAdapter.TripViewHolder>() {

    interface OnClick {
        fun onClick(uid: Int)
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

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(trip: Trip) {
            with (itemView) {
                descriptionView.text = trip.description
                dateView.text = "${trip.date}"
            }
        }

        override fun onClick(view: View) {
            handler.onClick(tripList[adapterPosition].uid)
        }
    }
}