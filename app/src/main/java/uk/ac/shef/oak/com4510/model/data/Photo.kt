package uk.ac.shef.oak.com4510.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.nio.file.Path
/**
 * Represents a photo
 * @property id The id of the photo in the Database
 * @property  path The path of the photo in the file system
 * @property metadata Additional metadata of the photo
 * @property tripId The trip id in which this photo is associated
 * @property sensorId The sensor id to associate the latest reading to a photo
 */

@Entity(tableName = "photo",
    foreignKeys = [
        ForeignKey(
            entity = Trip::class,
            parentColumns = ["id"],
            childColumns = ["tripId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Sensor::class,
            parentColumns = ["id"],
            childColumns = ["sensorId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val path: Path,
    val metadata: String,

    @ColumnInfo(index = true)
    val tripId: Int,
    @ColumnInfo(index = true)
    val sensorId: Int,
)