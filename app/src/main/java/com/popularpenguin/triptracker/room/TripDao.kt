package com.popularpenguin.triptracker.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.popularpenguin.triptracker.data.Trip

@Dao
interface TripDao {
    @Query("SELECT * FROM trip")
    suspend fun getAll(): List<Trip>

    @Query("SELECT * FROM trip WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<Trip>

    @Insert
    suspend fun insert(trip: Trip)

    @Insert
    suspend fun insertAll(vararg trips: Trip)

    @Delete
    suspend fun delete(trip: Trip)
}