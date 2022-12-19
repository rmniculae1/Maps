package uk.ac.shef.oak.com4510.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.constraintlayout.widget.ConstraintSet.Transform
import androidx.lifecycle.*
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.database.ImageEntity
import uk.ac.shef.oak.com4510.model.Image
import uk.ac.shef.oak.com4510.repository.ImageRepository
import uk.ac.shef.oak.com4510.repository.asDomainModel
import uk.ac.shef.oak.com4510.repository.asDomainModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ImageViewModel stores and manage UI-related data in a lifecycle aware way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * tasks can continue through configuration changes and deliver results after Fragment or Activity
 * is available.
 *
 * @param repository - data access is through the repository.
 */
class ImageViewModel(private val repository: ImageRepository, private val applicationContext: Application) : AndroidViewModel(applicationContext) {

    // Receive the Flow of ImageEntity data from the repository, but transform to the LiveData of Images
    // that will be observed fom the view
    val images: LiveData<List<Image>> = Transformations.map(repository.images){
        it.asDomainModels(applicationContext)
    } as MutableLiveData<List<Image>>
    /**
     * Retrieves a single Image object for the specified id
     */
    fun getImage(id: Int) : LiveData<Image> = Transformations.map(repository.getImage(id)){
        it.asDomainModel(applicationContext)
    }

    /**
     * Launching a new coroutine to INSERT an Image object in a non-blocking way
     *
     * Usinh the viewModelScope means this function (and others below), don't need to be
     * suspending. Allowing the function to be directly consumable
     * from the view classes without declaring a coroutine scope in the view.
     */
    fun insert(image: Image) = viewModelScope.launch {
        repository.insert(image)
    }

    /**
     * Inserts an Image into the database providing property values of an Image object
     */
    fun insert(
        image_uri: Uri,
        title: String ="title",
        description: String? = null
    ) = viewModelScope.launch {
        repository.insert(
            image_uri = image_uri,
            title = title,
            description = description,
            context = applicationContext)
    }

    /**
     * Launching a new coroutine to UPDATE an Image object in a non-blocking way
     */
    fun update(image: Image) = viewModelScope.launch {
        repository.update(image)
    }

    /**
     * Launching a new coroutine to DELETE an Image object in a non-blocking way
     */
    fun delete(image: Image) = viewModelScope.launch {
        repository.delete(image)
    }

}

// Extends the ViewModelProvider.Factory allowing us to control the viewmodel creation
// and provide the right parameters
class ImageViewModelFactory(private val repository: ImageRepository,  private val applicationContext: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(repository, applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}