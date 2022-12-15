package uk.ac.shef.oak.com4510.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "trip")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val date: Date,
    val title: String,
)