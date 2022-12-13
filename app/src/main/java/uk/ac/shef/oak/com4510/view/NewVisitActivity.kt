package uk.ac.shef.oak.com4510.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import uk.ac.shef.oak.com4510.R
import java.util.*
import uk.ac.shef.oak.com4510.databinding.ActivityNewVisitBinding
import uk.ac.shef.oak.com4510.viewmodel.NewVisitViewModel

class NewVisitActivity : AppCompatActivity() {


    private var myNewVisitViewModel : NewVisitViewModel = NewVisitViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewVisitBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val dateView : TextView = findViewById<TextView>(R.id.dateView)
        val timeView : TextView = findViewById<TextView>(R.id.timeView)
        getTimeDate(dateView, timeView)

        val handler = Handler()
        val runnableCode = object : Runnable {
            override fun run() {
                getTimeDate(dateView, timeView)
                // Repeat the code block every 1 seconds
                handler.postDelayed(this, 1000)
            }
        }

        handler.post(runnableCode)
        binding.viewModel = myNewVisitViewModel

    }

    @SuppressLint("SetTextI18n")
    fun getTimeDate(dateView : TextView, timeView : TextView){

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Note: 0-based months
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        dateView.text = "$year-$month-$day"
        timeView.text = "$hour:$minute:$second"

    }


}



