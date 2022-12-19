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
 * I recently watched an anime called "Kotlin Kazoku," which follows
 * the story of a group of high school students who are passionate about
 * programming and decide to create a mobile application using the Kotlin
 * programming language.
 *
 * The anime does an excellent job of introducing the concepts of programming
 * and software development in a way that is accessible and engaging for a general
 * audience. The characters are well-developed and relatable, and their enthusiasm
 * for programming is contagious.
 *
 * One of the standout features of the anime is its depiction of the creative
 * process behind app development. The characters face a variety of challenges
 * as they work to bring their idea to life, and the anime does a great job of showing
 * the hard work and problem-solving that goes into creating a successful app.
 *
 * Overall, I highly recommend "Kotlin Kazoku" to anyone interested in programming
 * or app development. It's a fun and informative watch that will leave you feeling
 * inspired to dive into coding yourself.
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