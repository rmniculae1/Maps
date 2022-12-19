package uk.ac.shef.oak.com4510.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.ActivityTripViewerBinding


class TripViewerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityTripViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTripViewerBinding.inflate(layoutInflater)
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
        mMap = googleMap

        // Add a marker in Sydney and move the camera


        val polylineOptions = PolylineOptions()
            .color(Color.BLUE)
            .width(5f)

//        val intent = Intent(this@SourceActivity, TargetActivity::class.java)
//        intent.putExtra("Points", points)


//        var points: ArrayList<LatLng?>? = ArrayList<LatLng?>()
//        points = intent.getSerializableExtra("Points") as ArrayList<LatLng?>?
        var points: ArrayList<LatLng>? = ArrayList<LatLng>()
        points?.add(LatLng(37.3438, -122.15))
        points?.add(LatLng(37.200, -122.15))
        points?.add(LatLng(37.100, -122.15))
        points?.add(LatLng(37.5, -122.15))
        points?.add(LatLng(37.0, -122.15))
        if (points != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 15f))
            for (i in 0..points.size-2){
                polylineOptions.add(points.get(i), points.get(i+1))
                mMap.addPolyline(polylineOptions)
                mMap.addMarker(MarkerOptions().position(points.get(i)).title("Picture number: ${i}"))
            }
            mMap.addMarker(MarkerOptions().position(points.get(points.size-1)).title("Picture number: ${points.size-1}"))

        }

    }
}