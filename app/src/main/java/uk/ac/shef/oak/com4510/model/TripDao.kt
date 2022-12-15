package uk.ac.shef.oak.com4510.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ac.shef.oak.com4510.model.data.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Query("SELECT * from photo " +
            "JOIN sensor on sensor.id = photo.sensor_id"):
    fun

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: Trip)

}