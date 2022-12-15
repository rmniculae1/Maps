package uk.ac.shef.oak.com4510.model.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date


@Entity(tableName = "trip")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val date: Date,
    val title: String,
)