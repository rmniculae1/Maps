package uk.ac.shef.oak.com4510.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.nio.file.Path

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
    val id: Int,
    val path: Path,
    val metadata: String,

    @ColumnInfo(index = true)
    val tripId: Int,
    @ColumnInfo(index = true)
    val sensorId: Int,
)