package uk.ac.shef.oak.com4510.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.shef.oak.com4510.model.data.Trip
/**
 * TripDao
 *
 * This class provides an instance of a Dao to interact with the trip table
 * It has useful functions to execute queries on the said table
 *
 */
@Dao
interface TripDao {

    @Query("SELECT * from trip where id = :given_id")
    fun getTrip(given_id: Int) : Flow<Trip>

    @Query("SELECT * from trip")
    suspend fun getAllTrips() : Flow<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: Trip): Long

    @Query("DELETE FROM trip")
    suspend fun deleteAllTrips()
}