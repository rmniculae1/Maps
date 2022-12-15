package uk.ac.shef.oak.com4510.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.nio.file.Path

@Entity(tableName = "photo",
[
    ForeignKey
])
data class Photo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val path: Path,
    val metadata: String,
    val sensor_id: Int
)
