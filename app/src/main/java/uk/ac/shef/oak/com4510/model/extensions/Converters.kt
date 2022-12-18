package uk.ac.shef.oak.com4510.model.extensions

import androidx.room.TypeConverter
import java.nio.file.Path
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.pathString

/**
 * Type Converters
 *
 * This class provides useful functions to convert complex types
 * into basic types for the database. The DB holds only basic types.
 *
 */


class Converters {
    /**
     *
     * Converts a timestamp(in ticks) to a Date
     * @param value valueToBeConverted
     * @return Date object
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     *
     * Converts a Date to a timestamp(in ticks)
     * @param value valueToBeConverted
     * @return converted value
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    /**
     *
     * Converts a Path to a string
     * @param path path that needs conversion
     * @return string value of the path
     */
    @TypeConverter
    fun pathToString(path: Path) : String{
        return path.pathString
    }

    /**
     *
     * Converts a string to a Path
     * @param string string that needs conversion
     * @return Path object
     */
    @TypeConverter
    fun stringToPath(string: String) : Path{
        return Path(string)
    }




}