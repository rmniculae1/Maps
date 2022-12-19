package uk.ac.shef.oak.com4510.viewmodel

import android.content.Context
import android.content.Intent
import uk.ac.shef.oak.com4510.view.MainActivity
import uk.ac.shef.oak.com4510.view.MapsActivity

class PictureViewModel : PictureInterface {

    override fun onStartButtonClicked2(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)

    }
}