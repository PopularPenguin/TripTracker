package com.popularpenguin.triptracker.room

import android.net.Uri
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
        fun fromUri(uri: Uri?): String {
            return uri?.toString() ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun toUri(str: String): Uri? {
            return if (str.isEmpty()) {
                null
            } else {
                Uri.parse(str)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromUriList(uriList: List<Uri>): String {
            val sb = StringBuilder()

            uriList.forEach {
                sb.append("$it;")
            }

            return sb.toString()
        }

        @TypeConverter
        @JvmStatic
        fun toUriList(str: String): List<Uri> {
            if (str.isEmpty()) {
                return listOf()
            }

            val uriList = mutableListOf<Uri>()
            val uriStrings = str.split(";")

            uriStrings.forEach {
                if (it.isNotEmpty()) {
                    uriList.add(Uri.parse(it))
                }
            }

            return uriList
        }
    }
}