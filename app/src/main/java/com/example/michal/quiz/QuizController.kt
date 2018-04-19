package com.example.michal.quiz

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by michal on 4/16/18.
 */
class QuizController {
    private var questions : ArrayList<Question> = ArrayList()
    private var currentQuestion = 0
    var userScore = 0

    /*
    Loading all questions and answers into controller and swapping them randomly
    so that they are displayed in random order
     */


    fun getCurrentQuestion() : String{
        return questions[currentQuestion].question;
    }

    fun getCurrentAnswerOfIndex(index : Int) : String{
        return questions[currentQuestion].getCurrentAswerOfNumber(index)
    }

    fun checkAnswer(submittedAnswer : String) : Boolean{
        currentQuestion++
        val result =questions[currentQuestion-1].isCorrect(submittedAnswer)

        if (result){
            userScore++
        }

        return result
    }

    fun getCurrentIndex() : Int{
        return currentQuestion
    }

    fun getCorrectAnswer() : Int{
        return questions[currentQuestion-1].correctAnswer
    }

    fun hasNextQuestion() : Boolean{
        return currentQuestion != questions.size
    }

    fun setArrayQuestions(arrayQuestions : ArrayList<Question>){
        var _questions = arrayQuestions
        val randomNumber : Int = Random().nextInt()

        for (i in 0..(_questions.size-1)){
            var num = (randomNumber+i)%_questions.size
            if (num < 0) num *= -1
            questions.add(_questions[num])
        }
    }

}