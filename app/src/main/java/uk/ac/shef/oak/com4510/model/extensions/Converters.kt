package uk.ac.shef.oak.com4510.model.extensions

import androidx.room.TypeConverter
import java.nio.file.Path
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.pathString

class Converters {

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