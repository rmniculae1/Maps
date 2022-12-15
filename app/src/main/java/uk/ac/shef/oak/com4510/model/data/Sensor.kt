package uk.ac.shef.oak.com4510.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "sensor", foreignKeys = [
    ForeignKey(entity = Sensor::class,
    parentColumns = ["id"],
    childColumns = ["trip_id"],
    onDelete = ForeignKey.CASCADE)
])
data class Sensor(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val cord: LatLng,
    val temp: Double,
    val pres: Double,
    val trip_id: Int

)
