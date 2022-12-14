package uk.ac.shef.oak.com4510.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uk.ac.shef.oak.com4510.view.MapsActivity
import uk.ac.shef.oak.com4510.view.NewVisitActivity

class NewVisitViewModel : VisitInterface {


    override fun onStartButtonClicked(context: Context) {
        val intent = Intent(context, MapsActivity::class.java)
        context.startActivity(intent)

    }
}