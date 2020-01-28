package com.auas.finalassignment.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.auas.finalassignment.database.TripRepository
import com.auas.finalassignment.model.Trip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val tripRepository =
        TripRepository(application.applicationContext)

    val trips: LiveData<List<Trip>> = tripRepository.getAllTrips()

    fun insertTrip(trip: Trip) {
        ioScope.launch {
            tripRepository.insertTrip(trip)
        }
    }

    fun deleteTrip(trip: Trip) {
        ioScope.launch {
            tripRepository.deleteTrip(trip)
        }
    }

    fun updateTrip(trip: Trip) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                tripRepository.updateTrip(trip)
            }
        }
    }


}
