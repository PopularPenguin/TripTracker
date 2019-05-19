package com.popularpenguin.triptracker.map

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.popularpenguin.triptracker.R

class TrackerNotification : Service() {

    companion object {
        private const val CHANNEL_ID = "trip_tracker_channel"
        private const val CHANNEL_NAME = "Trip Tracker"
        private const val notificationId = 1

        fun getServiceConnection(context: Context): ServiceConnection {
            return object : ServiceConnection {
                lateinit var service: Service

                override fun onServiceConnected(className: ComponentName?, binder: IBinder) {
                    service = (binder as NotificationBinder).service

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        service.startForegroundService(Intent(context, TrackerNotification::class.java))
                    } else {
                        service.startService(Intent(context, TrackerNotification::class.java))
                    }
                }

                override fun onServiceDisconnected(className: ComponentName?) { }
            }
        }
    }

    private val binder = NotificationBinder(this)

    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onCreate() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        create()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        cancel()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        cancel()

        return true
    }

    private fun create() {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }

        val notification = builder.setContentTitle(resources.getString(R.string.notification_title))
            .setContentText(resources.getString(R.string.notification_content))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

        notificationManager.notify(notificationId, notification)

        startForeground(notificationId, notification)
    }

    private fun cancel() {
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

    inner class NotificationBinder(val service: Service) : Binder()
}