package uk.ac.shef.oak.com4510.viewmodel

import android.content.Context
import android.content.Intent
import uk.ac.shef.oak.com4510.view.MapsActivity
import uk.ac.shef.oak.com4510.view.NewVisitActivity

class MapsViewModel : MapsInterface{

    override fun onStopButtonClicked(context: Context) {
        val intent = Intent(context, NewVisitActivity::class.java)
        context.startActivity(intent)
    }
}