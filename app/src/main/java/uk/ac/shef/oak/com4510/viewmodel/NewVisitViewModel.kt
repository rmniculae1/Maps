package uk.ac.shef.oak.com4510.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import uk.ac.shef.oak.com4510.view.MapsActivity

class NewVisitViewModel : VisitInterface {
     override fun onStartButtonClicked(context : Context) {
        Log.d("cacat","cacayt" )
        val intent = Intent(context, MapsActivity::class.java)
        context.startActivity(intent)
    }
}