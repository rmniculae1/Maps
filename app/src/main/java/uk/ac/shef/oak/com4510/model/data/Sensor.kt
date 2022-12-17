package uk.ac.shef.oak.com4510.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents a Sensor
 * @property id id of the sensor in the Database
 * @property latitude latitude of a reading
 * @property longitude longitude of a reading
 * @property temperature temperature of a reading
 * @property pressure pressure of a reading
 * @property tripId trip id in which this sensor is associated
 */

@Entity(
    foreignKeys = [ForeignKey(
        entity = Trip::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tripId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Sensor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val pressure: Double,
    val temperature: Double,

    @ColumnInfo(index = true)
    val tripId: Int,
)
