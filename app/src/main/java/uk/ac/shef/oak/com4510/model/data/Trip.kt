package uk.ac.shef.oak.com4510.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a Trip
 * @property id id of the trip in the Database
 * @property date date of the trip
 * @property title name of the trip
 */

@Entity(tableName = "trip")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val date: Date,
    val title: String,
)