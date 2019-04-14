package com.popularpenguin.triptracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
class Trip() {
    @PrimaryKey var uid = 0
    var day = 1
    var month = 1
    var year = 2000
    var description = ""
    var points = ""
    @ColumnInfo(name = "total_distance") var totalDistance = 0.0

    constructor(
        id: Int,
        desc: String,
        day: Int,
        month: Int,
        year: Int,
        points: String,
        distance: Double
    ): this() {
        uid = id
        description = desc
        this.day = day
        this.month = month
        this.year = year
        this.points = points
        totalDistance = distance
    }
}

fun Trip.getLatLng(points: String): List<LatLng> {

    return listOf(LatLng(0.0, 0.0)) // TODO: Conversion
}