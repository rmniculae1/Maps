package uk.ac.shef.oak.com4510.model.data

import androidx.annotation.WorkerThread
import uk.ac.shef.oak.com4510.model.TripDao
import kotlinx.coroutines.flow.Flow

class TripRepositry {
    // Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
    class WordRepository(private val tripDao: TripDao) {

        // Room executes all queries on a separate thread.
        // Observed Flow will notify the observer when the data has changed.
        val allTrips: Flow<List<Trip>> = tripDao.getAllTrips()

        // By default Room runs suspend queries off the main thread, therefore, we don't need to
        // implement anything else to ensure we're not doing long running database work
        // off the main thread.
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(trip: Trip) {
            tripDao.insert(trip)
        }
    }
}