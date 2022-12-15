package uk.ac.shef.oak.com4510.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.shef.oak.com4510.model.data.Trip

@Dao
interface TripDao {

    @Query("SELECT * from trip where id = :given_id")
    fun getTrip(given_id: Int) : Flow<Trip>

    @Query("SELECT * from trip")
    suspend fun getAllTrips() : List<Trip>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: Trip)

    @Query("DELETE FROM trip")
    suspend fun deleteAllTrips()
}