package com.popularpenguin.triptracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Trip() {
    @PrimaryKey var uid = 0
    var day = 1
    var month = 1
    var year = 2000
    var description = ""
    @ColumnInfo(name = "total_distance") var totalDistance = 0.0
    //var points: List<Pair<Float, Float>> = emptyList() // TypeConverter needed

    constructor(
        id: Int,
        desc: String,
        day: Int,
        month: Int,
        year: Int,
        distance: Double,
        p: List<Pair<Float, Float>>
    ): this() {
        uid = id
        description = desc
        this.day = day
        this.month = month
        this.year = year
        totalDistance = distance
        //points = p
    }
}