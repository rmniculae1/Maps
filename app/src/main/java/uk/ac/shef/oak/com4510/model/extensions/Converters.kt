package uk.ac.shef.oak.com4510.model.extensions

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.ac.shef.oak.com4510.model.data.Photo
import uk.ac.shef.oak.com4510.model.data.Sensor
import java.nio.file.Path
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.pathString

class Converters {

    var gson = Gson()

    @TypeConverter
    fun PhotoToString(photo: Photo) : String{
        return gson.toJson(photo)
    }

    @TypeConverter
    fun StringToPhoto(string: String) : Photo{
        return gson.fromJson(string, Photo::class.java)
    }

    @TypeConverter
    fun SensorToString(sensor: Sensor) : String{
        return gson.toJson(sensor)
    }

    @TypeConverter
    fun StringToSensor(string: String) : Sensor{
        return gson.fromJson(string, Sensor::class.java)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun pathToString(path: Path) : String{
        return path.pathString
    }

    @TypeConverter
    fun stringToPath(string: String) : Path{
        return Path(string)
    }




}