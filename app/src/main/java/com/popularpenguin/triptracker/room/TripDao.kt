package com.popularpenguin.triptracker.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.popularpenguin.triptracker.data.Trip

@Dao
interface TripDao {
    @Query("SELECT * FROM trip ORDER BY date DESC")
    suspend fun getAll(): List<Trip>

    @Query("SELECT * FROM trip WHERE date BETWEEN (:startDate) AND (:endDate)")
    suspend fun loadByDate(startDate: Long, endDate: Long): List<Trip>

    @Query("SELECT * FROM trip WHERE uid IS (:uid)")
    suspend fun loadById(uid: Int): Trip

    @Query("SELECT * FROM trip WHERE uid IN (:uids) ORDER BY date DESC")
    suspend fun loadAllByIds(uids: IntArray): List<Trip>

    @Insert
    suspend fun insert(trip: Trip)

    @Insert
    suspend fun insertAll(vararg trips: Trip)

    @Delete
    suspend fun delete(trip: Trip)
}