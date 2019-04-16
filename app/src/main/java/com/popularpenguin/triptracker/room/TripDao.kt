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

    @Query("SELECT * FROM trip WHERE day IN (:day) AND month IN (:month) AND year IN (:year)")
    suspend fun loadByDayMonthYear(day: Int, month: Int, year: Int): List<Trip>

    @Query("SELECT * FROM trip WHERE month IN (:month) AND year IN (:year)")
    suspend fun loadByMonthYear(month: Int, year: Int): List<Trip>

    @Query("SELECT * FROM trip WHERE year IN (:year)")
    suspend fun loadByYear(year: Int): List<Trip>

    @Insert
    suspend fun insertAll(vararg trips: Trip)

    @Delete
    suspend fun delete(trip: Trip)
}