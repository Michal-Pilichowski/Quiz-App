package com.example.michal.quiz

import android.os.Bundle
import android.app.Activity



/**
 * Created by michal on 4/18/18.
 */
class SettingsActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        fragmentManager.beginTransaction().add(R.id.settings_container, SettingsFragment()).commit()
    }


}