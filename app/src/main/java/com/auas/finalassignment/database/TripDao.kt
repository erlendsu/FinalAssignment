package com.auas.finalassignment.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.auas.finalassignment.model.Trip


@Dao
interface TripDao {

    @Query("SELECT * FROM tripTable")
    fun getAllTrips(): LiveData<List<Trip>>

    @Insert
    fun insertTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)

    @Update
    suspend fun updateTrip(trip: Trip)

}