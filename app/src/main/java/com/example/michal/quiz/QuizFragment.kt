package com.example.michal.quiz

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.util.*
import android.preference.PreferenceManager
import android.content.SharedPreferences



/**
 * Created by michal on 4/17/18.
 */

class QuizFragment : Fragment() {
    private var countdownTime = 30
    private val PREFERENCETIME = "COUNTDOWN_TIME"
    private var controller = QuizController()
    /*
    Array with layout elements:
    0 - answer 1
    1 - answer 2
    2 - answer 3
    3 - answer 4
    4 - question number
    5 - question text
     */
    private  var layoutElements = ArrayList<TextView>()

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val rootView = inflater?.inflate(R.layout.fragment_quiz, container, false)

        /*
       Preparing array with questions, question controller and setting first question
        */
        var questions : ArrayList<Question> = ArrayList()
        questions.add(Question(resources.getString(R.string.question_1), resources.getString(R.string.answer_1_1),
                resources.getString(R.string.answer_1_2), resources.getString(R.string.answer_1_3),
                resources.getString(R.string.answer_1_4), 1))

        questions.add(Question(resources.getString(R.string.question_2), resources.getString(R.string.answer_2_1),
                resources.getString(R.string.answer_2_2), resources.getString(R.string.answer_2_3),
                resources.getString(R.string.answer_2_4), 4))

        questions.add(Question(resources.getString(R.string.question_3), resources.getString(R.string.answer_3_1),
                resources.getString(R.string.answer_3_2), resources.getString(R.string.answer_3_3),
                resources.getString(R.string.answer_3_4), 2))

        questions.add(Question(resources.getString(R.string.question_4), resources.getString(R.string.answer_4_1),
                resources.getString(R.string.answer_4_2), resources.getString(R.string.answer_4_3),
                resources.getString(R.string.answer_4_4), 3))

        questions.add(Question(resources.getString(R.string.question_5), resources.getString(R.string.answer_5_1),
                resources.getString(R.string.answer_5_2), resources.getString(R.string.answer_5_3),
                resources.getString(R.string.answer_5_4), 3))

        //val cont = QuizController(questions)
        controller.setArrayQuestions(questions)

        layoutElements.add(rootView?.findViewById<TextView>(R.id.answer_1) as TextView)
        layoutElements.add(rootView?.findViewById<TextView>(R.id.answer_2) as TextView)
        layoutElements.add(rootView?.findViewById<TextView>(R.id.answer_3) as TextView)
        layoutElements.add(rootView?.findViewById<TextView>(R.id.answer_4) as TextView)
        layoutElements.add(rootView?.findViewById<TextView>(R.id.quiz_question_number) as TextView)
        layoutElements.add(rootView?.findViewById<TextView>(R.id.question_text) as TextView)

        layoutElements[4].text = "Question: " + (controller.getCurrentIndex()+1)
        layoutElements[5].text = controller.getCurrentQuestion()
        layoutElements[0].text = controller.getCurrentAnswerOfIndex(0)
        layoutElements[1].text = controller.getCurrentAnswerOfIndex(1)
        layoutElements[2].text = controller.getCurrentAnswerOfIndex(2)
        layoutElements[3].text = controller.getCurrentAnswerOfIndex(3)

        /*
        Creating progress bar.
        Duration of countdown and it's animation is read from preferences.
        Default value is 30 seconds.
         */
        try {
            val timePreferences = PreferenceManager.getDefaultSharedPreferences(activity)
            countdownTime = timePreferences.getInt(PREFERENCETIME, 0)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        val progress = rootView.findViewById<ProgressBar>(R.id.progressBar)

        val countdown = object : CountDownTimer(countdownTime.toLong()*1000, 10){
            override fun onFinish() {
                progress.progress = 0
                displayCorrectAnswer()
                displayNextQuestion()
            }

            override fun onTick(p0: Long) {
                progress.progress = (countdownTime*1000 - p0.toInt()) / (countdownTime*10)
            }
        }

        countdown.start()


        /*
        Creating on click listener to handle users answer choice
         */
        for (i in 0..3){
            layoutElements[i].setOnClickListener({
                countdown.cancel()
                if (controller.checkAnswer(layoutElements[i].text.toString())){
                    layoutElements[i].background = resources.getDrawable(R.drawable.hex_green)

                } else {
                    layoutElements[i].background = resources.getDrawable(R.drawable.hex_red)
                    displayCorrectAnswer()
                }

                countdown.start()
                displayNextQuestion()
            })
        }

        return rootView
    }

    /*
 Method resetting background colors of answer buttons
  */
    private fun resetAnswersBackground(){
        layoutElements[0].background = resources.getDrawable(R.drawable.hex_blue)
        layoutElements[1].background = resources.getDrawable(R.drawable.hex_blue)
        layoutElements[2].background = resources.getDrawable(R.drawable.hex_blue)
        layoutElements[3].background = resources.getDrawable(R.drawable.hex_blue)
    }

    /*
    Private method enabling or disabling on click of answer buttons
     */
    private fun setAnswerClicable(toClick : Boolean){
        layoutElements[0].isClickable = toClick
        layoutElements[1].isClickable = toClick
        layoutElements[2].isClickable = toClick
        layoutElements[3].isClickable = toClick
    }

    /*
    Method displaying correct answer
     */
    private fun displayCorrectAnswer(){
        when (controller.getCorrectAnswer()){
            1 -> layoutElements[0].background = resources.getDrawable(R.drawable.hex_green)
            2 -> layoutElements[1].background = resources.getDrawable(R.drawable.hex_green)
            3 -> layoutElements[2].background = resources.getDrawable(R.drawable.hex_green)
            4 -> layoutElements[3].background = resources.getDrawable(R.drawable.hex_green)
        }
    }

    /*
    Method displaying next question after time delay if possible
     */
    private fun displayNextQuestion(){
        if (controller.hasNextQuestion()){
            setAnswerClicable(false)
            val handler = Handler()
            val runnable = Runnable {
                layoutElements[4].text = "Question: " + (controller.getCurrentIndex()+1)
                layoutElements[5].text = controller.getCurrentQuestion()
                layoutElements[0].text = controller.getCurrentAnswerOfIndex(0)
                layoutElements[1].text = controller.getCurrentAnswerOfIndex(1)
                layoutElements[2].text = controller.getCurrentAnswerOfIndex(2)
                layoutElements[3].text = controller.getCurrentAnswerOfIndex(3)

                setAnswerClicable(true)
                resetAnswersBackground()

            }

            handler.postDelayed(runnable, 1200)

        } else {
            var num = controller.userScore
            val fragment = activity.supportFragmentManager.findFragmentByTag("quiz")
            activity.supportFragmentManager.beginTransaction().remove(fragment)
            var bundle = Bundle()
            bundle.putInt("points", controller.userScore)
            val scoreFragment = ScoreFragment()
            scoreFragment.arguments = bundle

            val runnable = Runnable { activity.supportFragmentManager.beginTransaction().add(R.id.main_layout_container, scoreFragment, "score").commit() }
            val hand = Handler()
            hand.postDelayed(runnable, 1200)
        }


    }


}