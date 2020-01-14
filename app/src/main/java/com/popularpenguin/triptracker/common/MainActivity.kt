package com.popularpenguin.triptracker.common

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.popularpenguin.triptracker.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 *  Trip and Photo Tracker by Brian Niedzialkoski
 *
 *  App icons from https://www.iconfinder.com/iconsets/nature-37 @Raisul Hadi
 *  https://www.iconfinder.com/icons/2998131/camera_photo_photography_icon
 *  Creative Commons (Attribution 3.0 Unported)
 *
 *  PhotoView library https://github.com/chrisbanes/PhotoView @Chris Banes
 *  Copyright 2018 Chris Banes
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
                MaterialDialog(this).show {
                    lifecycleOwner(this@MainActivity)

                    message(R.string.dialog_about_text)
                    cornerRadius(10f)
                    positiveButton(R.string.dialog_about_ok)
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
