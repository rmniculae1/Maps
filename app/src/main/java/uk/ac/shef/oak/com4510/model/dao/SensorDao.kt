package uk.ac.shef.oak.com4510.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.shef.oak.com4510.model.data.Sensor
/**
 * SensorDao
 *
 * This class provides an instance of a Dao to interact with the sensor table
 * It has useful functions to execute queries on the sensor table
 *
 */
@Dao
interface SensorDao {

    @Query("SELECT * from sensor where id = :given_id")
    fun getSensor(given_id: Int) : Flow<Sensor>

    @Query("SELECT * from sensor")
    fun getAllSensors() : Flow<List<Sensor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sensor: Sensor): Long

    @Query("DELETE FROM sensor")
    suspend fun deleteAllSensors()
}