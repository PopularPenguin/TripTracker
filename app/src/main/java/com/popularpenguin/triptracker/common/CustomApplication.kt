package com.popularpenguin.triptracker.common

import android.app.Application
import androidx.room.Room
import com.popularpenguin.triptracker.room.AppDatabase

class CustomApplication : Application() {

    lateinit var roomDb: AppDatabase

    override fun onCreate() {
        super.onCreate()

        roomDb = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tripdb").build()
    }
}