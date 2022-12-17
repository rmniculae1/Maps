package uk.ac.shef.oak.com4510.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.shef.oak.com4510.model.data.Sensor

@Dao
interface SensorDao {

    @Query("SELECT * from sensor where id = :given_id")
    fun getSensor(given_id: Int) : Flow<Sensor>

    @Query("SELECT * from sensor")
    fun getAllSensors() : Flow<List<Sensor>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sensor: Sensor)

    @Query("DELETE FROM sensor")
    suspend fun deleteAllSensors()
}