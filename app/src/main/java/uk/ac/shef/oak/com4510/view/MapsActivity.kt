package uk.ac.shef.oak.com4510.view


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.runBlocking
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.ActivityMapsBinding
import uk.ac.shef.oak.com4510.model.data.AppDatabase
import uk.ac.shef.oak.com4510.model.data.Trip
import uk.ac.shef.oak.com4510.model.data.Sensor as ModelSensor
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer

/**
 * MapsActivity
 *
 * An activity that displays a map on the screen.
 * It uses the Google Maps API to show the map and current user location every 20 seconds
 * It uses the barometric sensor to update the pressure every 20 seconds
 * It allows users to take pictures and adds a tag in that location
 *
 */

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var txtView: TextView

    private var i = 0
    private var ok = 0
    private var tripId = 0
    private var sensorId = 0
    private var pressure = 0.0
    private var temperature = 0.0

    private lateinit var sensorManager: SensorManager
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var mButtonEnd: Button? = null
    private var mButtonPic: Button? = null
    private var latlngPoint: Location? = null
    private var nextPoint: Location? = null

    private lateinit var title: String
    private lateinit var date: Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            title = extras.getString("title").toString()
            date = intent.getSerializableExtra("date") as Date
            if (title == "") {
                title = "No title"
            }
        }

        runBlocking {
            tripId = AppDatabase
                .getDatabase(applicationContext)
                .tripDao()
                .insert(Trip(tripId, date, title))
                .toInt()
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        txtView = findViewById(R.id.visitLabel)

        mButtonEnd = findViewById(R.id.button_end) as Button
        mButtonEnd!!.setOnClickListener {
            val intent = Intent(this, NewVisitActivity::class.java)
            this.startActivity(intent)
        }

        calculatePressure { pressure ->
            if (ok == 0) {
                if (i == 20) {

                    txtView.text =
                        "Pressure: $pressure" // Calculate pressure for the first time
                    ok = 1
                    mLocationRequest = LocationRequest.create().apply {
                        interval = 5000
                        fastestInterval = 5000
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    }
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    startLocationUpdates()


                }
            }
            if (ok == 1 && i == 30) {
//                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                val date: Date = dateFormat.parse(date.toString())
//                Log.d("test", latlngPoint.toString())
//                Log.d("test", pressure.toString())
//                Log.d("test", title)
//                Log.d("test", date.toString())
                stopLocationUpdates()
                ok = 2
            }

            if (i == 299 && ok == 2) {
                nextPoint = latlngPoint
            }
            if (i == 300 && ok == 2) {

                txtView.text =
                    "Pressure: " + pressure.toString()  // Update the label every 20 seconds


                mLocationRequest = LocationRequest.create().apply {
                    interval = 5000
                    fastestInterval = 5000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                startLocationUpdates()

//                Log.d("test", latlngPoint.toString())
//                Log.d("test", pressure.toString())
            } else if (i == 302 && ok == 2) {
                stopLocationUpdates()
                i = 0;
            }
            i++
        }

        fixedRateTimer("timer", false, 0L, 20 * 1000) {
            runBlocking {
                sensorId = AppDatabase
                    .getDatabase(applicationContext)
                    .sensorDao()
                    .insert(
                        ModelSensor(
                            latitude = latlngPoint?.latitude ?: 9999.0,
                            longitude = latlngPoint?.longitude ?: 9999.0,
                            pressure = pressure,
                            temperature = temperature,
                            tripId = tripId
                        )
                    ).toInt()
            }

        }

        mButtonPic = findViewById<View>(R.id.button_pic) as Button
        mButtonPic!!.setOnClickListener {
            val picLocation = LatLng(
                (latlngPoint?.latitude ?: 0) as Double,
                (latlngPoint?.longitude ?: 0) as Double
            )
            mMap.addMarker(MarkerOptions().position(picLocation).title("Picture"))
            val intent = Intent(this, PictureActivity::class.java)
            intent.putExtra("tripId", tripId)
            intent.putExtra("sensorId", sensorId)

            this.startActivity(intent)
        }

    }

    /**
     * it stops the location updates
     */
    fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)

        Log.d("MAP", "services stopped")
    }


    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    ACCESS_FINE_LOCATION
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return
        }
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            null /* Looper */
        )
    }


    private var mCurrentLocation: Location? = null
    private var mLastUpdateTime: String? = null
    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            mCurrentLocation = locationResult.getLastLocation()
            mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
            Log.i("MAP", "new location " + mCurrentLocation.toString())
            latlngPoint = mCurrentLocation
            if (mMap != null) mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        mCurrentLocation!!.latitude,
                        mCurrentLocation!!.longitude
                    )
                ).title(mLastUpdateTime)
            )
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        mCurrentLocation!!.latitude,
                        mCurrentLocation!!.longitude
                    ), 14.0f
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    mFusedLocationClient.requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback, null /* Looper */
                    )
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

    companion object {
        private const val ACCESS_FINE_LOCATION = 123
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
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        val polylineOptions = PolylineOptions()
            .color(Color.BLUE)
            .width(5f)

        // Adds the path
        if (nextPoint != null) {
            val pointA = LatLng(nextPoint!!.latitude, nextPoint!!.longitude)
            val pointB = LatLng(latlngPoint!!.latitude, latlngPoint!!.longitude)
            polylineOptions.add(pointA, pointB)
            mMap.addPolyline(polylineOptions)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointA, 15f))
        }


    }

    fun calculatePressure(callback: (Double) -> Unit) {
        // Use the barometric sensor to get the current pressure
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        if (pressureSensor != null) {
            sensorManager.registerListener(object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

                override fun onSensorChanged(event: SensorEvent?) {
                    if (event != null) {
                        pressure = event.values[0].toDouble()
                        val handler = Handler()
                        // Return the pressure value using the callback function

                        callback(pressure)
                    }
                }
            }, pressureSensor, SensorManager.SENSOR_DELAY_UI)


        } else {
            Log.d("ceva", "Error: No pressure sensor found")
        }
    }


}