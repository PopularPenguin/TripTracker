package com.popularpenguin.triptracker.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.popularpenguin.triptracker.data.Trip

@Dao
interface TripDao {
    @Query("SELECT * FROM trip")
    fun getAll(): List<Trip>

    @Query("SELECT * FROM trip WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Trip>

    @Query("SELECT * FROM trip WHERE day IN (:day) AND month IN (:month) AND year IN (:year)")
    fun loadByDayMonthYear(day: Int, month: Int, year: Int): List<Trip>

    @Query("SELECT * FROM trip WHERE month IN (:month) AND year IN (:year)")
    fun loadByMonthYear(month: Int, year: Int): List<Trip>

    @Query("SELECT * FROM trip WHERE year IN (:year)")
    fun loadByYear(year: Int): List<Trip>

    @Insert
    fun insertAll(vararg trips: Trip)

    @Delete
    fun delete(trip: Trip)
}