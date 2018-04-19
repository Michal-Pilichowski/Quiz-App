package com.example.michal.quiz

import android.content.SharedPreferences
import android.preference.PreferenceFragment
import android.R.xml
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView





/**
 * Created by michal on 4/18/18.
 */
class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val PREFERENCETIME = "COUNTDOWN_TIME"

    /*
    Registering shared preferences on change listener when starting fragment
     */
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.preferences)

        val currentValue = activity.findViewById<TextView>(R.id.text_view_current_value)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        currentValue.text = "" + sharedPreferences.getInt(PREFERENCETIME, 0)

    }

    /*
   Method updating current countdown value displayed to user
    */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        if (s == PREFERENCETIME) {
            val currentValue = activity.findViewById<TextView>(R.id.text_view_current_value)
            currentValue.text = "" + sharedPreferences.getInt(PREFERENCETIME, 0)
        }
    }

    /*
    Unregistering shared preferences on change listener
     */
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
    }
}