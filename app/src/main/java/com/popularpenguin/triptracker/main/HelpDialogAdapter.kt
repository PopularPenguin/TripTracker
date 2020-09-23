package com.popularpenguin.triptracker.main

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popularpenguin.triptracker.R
import kotlinx.android.synthetic.main.help_list_item.view.*

// For MaterialDialog's custom list
class HelpDialogAdapter() :
    RecyclerView.Adapter<HelpDialogAdapter.DialogViewHolder>() {

    // list of image and text resources for the list
    val viewData: List<Pair<Int, Int>> = listOf(
        Pair(0, R.string.dialog_help_title_list),
        Pair(android.R.drawable.ic_menu_compass, R.string.dialog_help_show),
        Pair(android.R.drawable.ic_menu_sort_alphabetically, R.string.dialog_help_search),
        Pair(android.R.drawable.ic_media_play, R.string.dialog_help_first),
        Pair(android.R.drawable.ic_menu_close_clear_cancel, R.string.dialog_help_second),
        Pair(android.R.drawable.ic_menu_add, R.string.dialog_help_start),
        Pair(0, R.string.dialog_help_title_map),
        Pair(android.R.drawable.ic_menu_zoom, R.string.dialog_help_zoom),
        Pair(android.R.drawable.ic_menu_mapmode, R.string.dialog_help_terrain),
        Pair(android.R.drawable.ic_menu_camera, R.string.dialog_help_camera),
        Pair(android.R.drawable.ic_media_play, R.string.dialog_help_track),
        Pair(android.R.drawable.star_big_off, R.string.dialog_help_caption)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.help_list_item, parent, false)

        return DialogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.bind(position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = viewData.size

    inner class DialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val imageRes = viewData[position].first
            val textRes = viewData[position].second

            itemView.helpImage
                .setImageResource(imageRes)

            itemView.helpText
                .setText(textRes)

            if (imageRes == 0) {
                itemView.helpImage
                    .visibility = View.GONE

                itemView.helpText
                    .textSize = 20.0f
            }
        }
    }
}