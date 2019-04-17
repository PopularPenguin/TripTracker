package com.popularpenguin.triptracker.map

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.popularpenguin.triptracker.R

class TrackerNotification(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "trip_tracker_channel"
        const val CHANNEL_NAME = "Trip Tracker"
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val notificationId = 1

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    fun createNotification() {
        val resources = context.resources
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, CHANNEL_ID)
        } else {
            Notification.Builder(context)
        }

        // TODO: Use string resources and create an icon for the notification
        val notification = builder.setContentTitle("TripTracker")
            .setContentText("Tracker is running")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    fun cancelNotification() {
        notificationManager.cancel(notificationId)
    }

    @TargetApi(26)
    private fun createChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            setSound(null, null)
        }

        notificationManager.createNotificationChannel(notificationChannel)
    }
}