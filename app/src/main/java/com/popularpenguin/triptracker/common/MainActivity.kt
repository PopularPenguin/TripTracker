package com.popularpenguin.triptracker.common

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.popularpenguin.triptracker.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 *  Brian Niedzialkoski
 *
 *  App icons from https://www.iconfinder.com/iconsets/nature-37 @Raisul Hadi
 *  https://www.iconfinder.com/icons/2998131/camera_photo_photography_icon
 *
 *  PhotoView library https://github.com/chrisbanes/PhotoView @Chris Banes
 *
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Set the fragment to the first screen of the app
        ScreenNavigator(supportFragmentManager).loadTripList()

        // check permissions
        PermissionValidator(this).requestPermissions()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {
                AboutDialog(this).show()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
