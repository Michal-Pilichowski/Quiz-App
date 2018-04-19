package com.example.michal.quiz

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * Created by michal on 4/17/18.
 */
class MenuFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val rootView = inflater?.inflate(R.layout.fragment_menu, container, false)

        /*
        Button starting quiz
         */
        val buttonStart = rootView?.findViewById<Button>(R.id.button_start_quiz)
        buttonStart?.setOnClickListener({
            var fragment = activity.supportFragmentManager.findFragmentByTag("menu")
            activity.supportFragmentManager.beginTransaction().remove(fragment).commit()
            activity.supportFragmentManager.beginTransaction().add(R.id.main_layout_container, QuizFragment(), "quiz").commit()
        })

        /*
        Button exiting the app
         */
        val buttonEnd = rootView?.findViewById<Button>(R.id.button_end_app)
        buttonEnd?.setOnClickListener({
            activity.finish()
            activity.moveTaskToBack(true)
        })

        /*
        Button setting time for answering
         */
        val buttonSettings = rootView?.findViewById<Button>(R.id.button_options)
        buttonSettings?.setOnClickListener({
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        })

        return rootView
    }

}