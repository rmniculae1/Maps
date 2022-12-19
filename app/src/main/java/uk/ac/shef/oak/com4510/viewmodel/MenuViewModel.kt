package uk.ac.shef.oak.com4510.viewmodel

import android.content.Context
import android.content.Intent
import uk.ac.shef.oak.com4510.view.PictureActivity
//import uk.ac.shef.oak.com4510.view.Gallery
import uk.ac.shef.oak.com4510.view.MapsActivity
import uk.ac.shef.oak.com4510.view.NewVisitActivity
import uk.ac.shef.oak.com4510.view.PicturesMapActivity
import uk.ac.shef.oak.com4510.viewmodel.MenuViewModelInterface

class MenuViewModelViewModel : MenuViewModelInterface {

    override fun startVisit(context: Context) {
        //Creates an Intent object for the activity that you want to open
        val intent = Intent(context, NewVisitActivity::class.java)

        // Starts the new activity using the Intent object
        context.startActivity(intent)
    }

    override fun startPicture(context: Context) {
        //Creates an Intent object for the activity that you want to open
        val intent = Intent(context, PictureActivity::class.java)

        // Starts the new activity using the Intent object
        context.startActivity(intent)

    }

    override fun startGallery(context: Context) {
//        //Creates an Intent object for the activity that you want to open
//        val intent = Intent(context, Gallery::class.java)
//
//        // Starts the new activity using the Intent object
//        context.startActivity(intent)

    }

    override fun startMap(context: Context) {
        //Creates an Intent object for the activity that you want to open
        val intent = Intent(context, PicturesMapActivity::class.java)

        // Starts the new activity using the Intent object
        context.startActivity(intent)

    }


}