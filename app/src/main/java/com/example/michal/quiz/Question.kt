package com.example.michal.quiz

/**
 * Created by michal on 4/16/18.
 */
class Question (private val _question : String, private val _answer1 : String,
                private val _answer2 : String, private val _answer3 : String,
                private val _answer4 : String, private val _correctAnswer : Int){

    var question : String = ""
    var answers : ArrayList<String> = ArrayList()
    var correctAnswer : Int = 0

    init {
        question = _question
        answers.add(_answer1)
        answers.add(_answer2)
        answers.add(_answer3)
        answers.add(_answer4)
        correctAnswer = _correctAnswer
    }

    fun isCorrect(submittedAnswer : String = "") : Boolean{
        return when (_correctAnswer){
            1 -> submittedAnswer == answers[0]
            2 -> submittedAnswer == answers[1]
            3 -> submittedAnswer == answers[2]
            4 -> submittedAnswer == answers[3]
            else -> false;
        }
    }

    fun getCurrentAswerOfNumber(index : Int) : String{
        return answers[index]
    }

}