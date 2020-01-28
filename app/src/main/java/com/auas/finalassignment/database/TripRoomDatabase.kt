package com.auas.finalassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.auas.finalassignment.model.Trip

@Database(entities = [Trip::class], version = 1, exportSchema = false)
abstract class TripRoomDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    companion object {
        private const val DATABASE_NAME = "TRIP_DATABASE"

        @Volatile
        private var tripRoomDatabaseInstance: TripRoomDatabase? = null

        fun getDatabase(context: Context): TripRoomDatabase? {
            if (tripRoomDatabaseInstance == null) {
                synchronized(TripRoomDatabase::class.java) {
                    if (tripRoomDatabaseInstance == null) {
                        tripRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            TripRoomDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return tripRoomDatabaseInstance
        }
    }

}