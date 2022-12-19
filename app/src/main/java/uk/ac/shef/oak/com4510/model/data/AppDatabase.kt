package uk.ac.shef.oak.com4510.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.ac.shef.oak.com4510.model.dao.PhotoDao
import uk.ac.shef.oak.com4510.model.dao.SensorDao
import uk.ac.shef.oak.com4510.model.dao.TripDao
import uk.ac.shef.oak.com4510.model.extensions.Converters

/**
 * Database
 *
 * This class provides the singleton that room uses to build the database
 *
 */

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Photo::class, Sensor::class, Trip::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    abstract fun photoDao(): PhotoDao

    abstract fun sensorDao(): SensorDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trip_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}