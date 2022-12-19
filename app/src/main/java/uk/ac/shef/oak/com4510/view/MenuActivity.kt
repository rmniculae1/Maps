package uk.ac.shef.oak.com4510.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.ActivityMenuBinding

import uk.ac.shef.oak.com4510.viewmodel.MenuViewModelViewModel

class MenuActivity : AppCompatActivity() {

    var myMenuActivityViewModel: MenuViewModelViewModel = MenuViewModelViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMenuModel = myMenuActivityViewModel
    }
}