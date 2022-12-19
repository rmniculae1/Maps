package uk.ac.shef.oak.com4510.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.ActivityMenuBinding

import uk.ac.shef.oak.com4510.viewmodel.MenuViewModelViewModel

class MenuActivity : AppCompatActivity() {

    var myMenuActivityViewModel: MenuViewModelViewModel = MenuViewModelViewModel()
    private var mButtonMap: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMenuModel = myMenuActivityViewModel

        mButtonMap = findViewById<View>(R.id.mapBtn) as Button
        mButtonMap!!.setOnClickListener {
            val intent = Intent(this, PicturesMapActivity::class.java)
            this.startActivity(intent)
        }
    }
}