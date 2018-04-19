package com.example.michal.quiz

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * Created by michal on 4/17/18.
 */
class ScoreFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val rootView = inflater?.inflate(R.layout.fragment_score, container, false)

        /*
        Setting users score
         */
        val userScore = rootView?.findViewById<TextView>(R.id.score_result)
        var num = arguments?.get("points")
        userScore?.text = "" + arguments?.getInt("points")

        /*
        Setting listeners
         */
        val buttonMenu = rootView?.findViewById<Button>(R.id.score_button_menu)
        buttonMenu?.setOnClickListener({
            val fragment = activity.supportFragmentManager.findFragmentByTag("score")
            activity.supportFragmentManager.beginTransaction().remove(fragment).commit()
            activity.supportFragmentManager.beginTransaction().add(R.id.main_layout_container, MenuFragment(), "menu").commit()
        })

        val buttonTry = rootView?.findViewById<Button>(R.id.score_button_retry)
        buttonTry?.setOnClickListener({
            val fragment = activity.supportFragmentManager.findFragmentByTag("score")
            activity.supportFragmentManager.beginTransaction().remove(fragment).commit()
            activity.supportFragmentManager.beginTransaction().add(R.id.main_layout_container, QuizFragment(), "quiz").commit()
        })

        return rootView
    }

}