package com.popularpenguin.triptracker.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity
class Trip() {
    @PrimaryKey(autoGenerate = true) var uid = 0
    var date = Date()
    var description = ""
    var points = mutableListOf<LatLng>()
    var captionPhoto = "" // displayed in TripList, if empty, first photo from photoList is shown
    var photoList = mutableListOf<String>() // list of photo links for display in single trip
    @ColumnInfo(name = "total_distance") var totalDistance = 0.0
    var uriList = mutableListOf<Uri>()

    constructor(
        id: Int,
        desc: String,
        date: Date,
        points: MutableList<LatLng>,
        captionPhoto: String,
        photoList: MutableList<String>,
        distance: Double,
        uriList: MutableList<Uri>
    ): this() {
        uid = id
        description = desc
        this.date = date
        this.points = points
        this.captionPhoto = captionPhoto
        this.photoList = photoList
        totalDistance = distance
        this.uriList = uriList
    }

    fun getFormattedDate(): String {
        return try {
            val formatter = SimpleDateFormat("MMMM dd, yyyy h:mm a", Locale.US)

            formatter.format(date)
        } catch (e: ParseException) {
            "Invalid date"
        }
    }
}