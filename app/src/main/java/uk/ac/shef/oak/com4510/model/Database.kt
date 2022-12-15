package uk.ac.shef.oak.com4510.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.ac.shef.oak.com4510.model.data.Trip
import uk.ac.shef.oak.com4510.model.extensions.Converters

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Trip::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TripDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getDatabase(context: Context): TripDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripDatabase::class.java,
                    "word_database")
                    .addTypeConverter(Converters::class)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}