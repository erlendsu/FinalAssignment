package com.auas.finalassignment.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.auas.finalassignment.model.Trip

class TripRepository(context: Context) {

    private var tripDao: TripDao

    init {
        val tripRoomDatabase = TripRoomDatabase.getDatabase(context)
        tripDao = tripRoomDatabase!!.tripDao()
    }

    fun getAllTrips(): LiveData<List<Trip>> {
        return tripDao.getAllTrips()
    }

    fun insertTrip(trip: Trip) {
        tripDao.insertTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip) {
        tripDao.deleteTrip(trip)
    }

    suspend fun updateTrip(trip: Trip) {
        tripDao.updateTrip(trip)
    }

}