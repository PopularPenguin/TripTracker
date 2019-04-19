package com.popularpenguin.triptracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
class Trip() {
    @PrimaryKey(autoGenerate = true) var uid = 0
    var date = 0L
    var description = ""
    var points = mutableListOf<LatLng>()
    @ColumnInfo(name = "total_distance") var totalDistance = 0.0

    constructor(
        id: Int,
        desc: String,
        date: Long,
        points: MutableList<LatLng>,
        distance: Double
    ): this() {
        uid = id
        description = desc
        this.date = date
        this.points = points
        totalDistance = distance
    }
}