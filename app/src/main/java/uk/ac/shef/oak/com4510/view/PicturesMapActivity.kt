package uk.ac.shef.oak.com4510.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.runBlocking
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.ActivityPicturesMapBinding
import uk.ac.shef.oak.com4510.model.data.AppDatabase

class PicturesMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityPicturesMapBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPicturesMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        val db = AppDatabase.getDatabase(applicationContext)
        val photoDao = db.photoDao()
        val sensorDao = db.sensorDao()
        mMap = googleMap
        val pics = photoDao.getAllPhotos()
        var lastPic: LatLng? = null

        runBlocking {
            pics.collect { pic ->
                // Process each list of integers
                pic.forEach { item ->
                    val picData = sensorDao.getSensor(item.sensorId).collect { sensor ->
                        val picLocation = LatLng(sensor.latitude, sensor.longitude)
                        mMap.addMarker(MarkerOptions().position(picLocation).title("Picture"))
                        lastPic = picLocation
                    }
                }
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastPic!!))


    }
}
