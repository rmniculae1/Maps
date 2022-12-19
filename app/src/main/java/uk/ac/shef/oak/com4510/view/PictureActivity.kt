package uk.ac.shef.oak.com4510.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uk.ac.shef.oak.com4510.databinding.ActivityPictureBinding
import uk.ac.shef.oak.com4510.model.data.AppDatabase
import uk.ac.shef.oak.com4510.model.data.Photo
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*

class PictureActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 0x23

    private var tripId = 0

    private var sensorId = 0

    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tripId = intent.extras?.getInt("tripId") ?: 0
        sensorId = intent.extras?.getInt("sensorId") ?: 0

        dispatchTakePictureIntent()
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_TRIP_${tripId}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                createImageFile().also {
                    photoUri = FileProvider.getUriForFile(
                        this,
                        "uk.ac.shef.oak.com4510.fileprovider",
                        it,
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("test", "photoUri: $photoUri")
            Log.d("test", "ok")
            Log.d("test", "sensor_id: $sensorId.toString()")
            val db = AppDatabase.getDatabase(applicationContext)
            val photoDao = db.photoDao()
            GlobalScope.launch {
                val photo_id = photoDao.insert(
                    Photo(
                        path = Paths.get(photoUri.path),
                        metadata = "",
                        tripId = tripId,
                        sensorId = sensorId
                    )
                )

                Log.d("test", "photoId: $photo_id")

            }

            finish()

        }
    }
}
