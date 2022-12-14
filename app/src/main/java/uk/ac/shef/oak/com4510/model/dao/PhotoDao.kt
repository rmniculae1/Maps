package uk.ac.shef.oak.com4510.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.shef.oak.com4510.model.data.Photo
/**
 * PhotoDao
 *
 * This class provides an instance of a Dao to interact with the photos table
 * It has useful functions to execute queries on the photos table
 *
 */
@Dao
interface PhotoDao {

    @Query("SELECT * from photo where id = :given_id")
    fun getPhoto(given_id: Int) : Flow<Photo>

    @Query("SELECT * from photo")
    fun getAllPhotos() : Flow<List<Photo>>

    @Query("SELECT * from photo where tripId = :given_id")
    fun getPhotosFromTrip(given_id: Int) : Flow<List<Photo>>

    @Query("SELECT * from photo where sensorId = :given_id")
    fun getPhotosFromSensor(given_id: Int) : Flow<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo): Long

    @Query("DELETE FROM photo")
    suspend fun deleteAllPhotos()
}