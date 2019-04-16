package com.popularpenguin.triptracker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.popularpenguin.triptracker.data.Trip

@Database(entities = [Trip::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var db: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if (db == null) {
                synchronized(this) {
                    db = Room.databaseBuilder(context, AppDatabase::class.java, "tripdb").build()
                }
            }

            return db!!
        }
    }

    abstract fun dao(): TripDao
}