package com.popularpenguin.triptracker.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.popularpenguin.triptracker.data.Trip

@Database(entities = arrayOf(Trip::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}