package com.popularpenguin.triptracker.room

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import java.util.*

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromDate(date: Date): Long {
            return date.time
        }

        @TypeConverter
        @JvmStatic
        fun toDate(unixTime: Long): Date {
            return Date(unixTime)
        }

        @TypeConverter
        @JvmStatic
        fun fromLatLng(latLngList: List<LatLng>): String {
            val sb = StringBuilder()

            latLngList.forEach {
                sb.append("${it.latitude},${it.longitude};")
            }

            return sb.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toLatLng(str: String): List<LatLng> {
            if (str.isEmpty()) {
                return listOf(LatLng(0.0, 0.0))
            }
            val latLngList = mutableListOf<LatLng>()
            val points = str.split(";")

            points.forEach {
                val latLngStrings = it.split(",")
                val latLng = LatLng(latLngStrings[0].toDouble(), latLngStrings[1].toDouble())

                latLngList.add(latLng)
            }

            return latLngList
        }
    }
}