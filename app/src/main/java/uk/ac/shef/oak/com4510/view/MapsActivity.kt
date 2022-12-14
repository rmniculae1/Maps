package uk.ac.shef.oak.com4510.view

import android.content.Context
import android.graphics.Color
import android.graphics.Insets.add
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.ActivityMapsBinding
import uk.ac.shef.oak.com4510.viewmodel.MapsViewModel
import uk.ac.shef.oak.com4510.viewmodel.NewVisitViewModel
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var txtView: TextView
    var i = 0
    var ok = 0
    var i1 = 0
    var ok1 = 0
    private lateinit var sensorManager: SensorManager
    private var myMapsViewModel : MapsViewModel = MapsViewModel()

    private var tempSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        txtView = findViewById(R.id.visitLabel)

        binding.viewModel = myMapsViewModel




        calculatePressure { pressure ->
            if (ok == 0) {
                Log.d("pres", pressure.toString())
                txtView.text =
                    "Pressure: " + pressure.toString() // Calculate pressure for the first time
                ok = 1
            }

            if (i == 300) {
                Log.d("update", pressure.toString())
                txtView.text =
                    "Pressure: " + pressure.toString()  // Update the label every 20 seconds
                i = 0;
            }
            i++

        }


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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        val pointA = LatLng(-33.852, 151.211)
        val pointB = LatLng(-33.854, 151.213)


        val polylineOptions = PolylineOptions()
            .color(Color.BLUE)
            .width(5f)


        polylineOptions.add(pointA, pointB)


        mMap.addPolyline(polylineOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointA, 15f))
    }

    fun calculatePressure(callback: (Double) -> Unit) {
        var pressure = 0.0 // Initialize pressure to 0.0

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