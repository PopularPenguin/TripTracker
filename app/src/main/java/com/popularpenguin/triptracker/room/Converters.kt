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
                if (it.isNotEmpty()) {
                    val latLngStrings = it.split(",")
                    val latLng = LatLng(latLngStrings[0].toDouble(), latLngStrings[1].toDouble())

                    latLngList.add(latLng)
                }
            }

            return latLngList
        }

        @TypeConverter
        @JvmStatic
        fun fromPhotoList(photoList: List<String>): String {
            val sb = StringBuilder()

            photoList.forEach { "${sb.append(it)};" }

            return sb.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toPhotoList(str: String): List<String> {
            if (str.isEmpty()) {
                return listOf()
            }

            val photoList = mutableListOf<String>()
            val photoStrings = str.split(";")

            photoStrings.forEach {
                if (it.isNotEmpty()) photoList.add(it)
            }

            return photoList
        }
    }
}