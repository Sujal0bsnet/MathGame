package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class GameActivity : AppCompatActivity() {
    private lateinit var textScore: TextView
    private lateinit var textLives: TextView
    private lateinit var textTimer: TextView
    private lateinit var textQuestion: TextView
    private lateinit var editTextAnswer: EditText
    private var score = 0
    private var lives = 3
    private var timer: CountDownTimer? = null
    private var operation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        textScore = findViewById(R.id.textScore)
        textLives = findViewById(R.id.textLives)
        textTimer = findViewById(R.id.textTimer)
        textQuestion = findViewById(R.id.textQuestion)
        editTextAnswer = findViewById(R.id.editTextAnswer)

        operation = intent.getStringExtra("operation")

        findViewById<View>(R.id.buttonSubmit).setOnClickListener { view: View? -> checkAnswer() }
        generateQuestion()
    }

    private fun generateQuestion() {
        textQuestion.text = when (operation) {
            "addition" -> generateAdditionQuestion()
            "subtraction" -> generateSubtractionQuestion()
            "multiplication" -> generateMultiplicationQuestion()
            "division" -> generateDivisionQuestion()
            else -> ""
        }
        startTimer()
    }

    private fun generateAdditionQuestion(): String {
        val a = (1..100).random()
        val b = (1..100).random()
        return "$a + $b"
    }

    private fun generateSubtractionQuestion(): String {
        var a: Int
        var b: Int
        do {
            a = (1..100).random()
            b = (1..100).random()
        } while (a < b)
        return "$a - $b"
    }

    private fun generateMultiplicationQuestion(): String {
        val a = (1..100).random()
        val b = (1..100).random()
        return "$a * $b"
    }

    private fun generateDivisionQuestion(): String {
        var a: Int
        var b: Int
        do {
            a = (1..100).random()
            b = (1..100).random()
        } while (a % b != 0)
        return "$a / $b"
    }


    private fun startTimer() {
        if (timer != null) {
            timer!!.cancel()
        }
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textTimer.text = "Time: " + millisUntilFinished / 1000
            }

            override fun onFinish() {
                loseLife()
            }
        }.start()
    }

    private fun checkAnswer() {
        if (timer != null) {
            timer!!.cancel()
        }
        val question = textQuestion.text.toString()
        val answerStr = editTextAnswer.text.toString()
        if (answerStr.isEmpty()) {
            Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show()
            startTimer()
            return
        }
        val correctAnswer = calculateAnswer(question)
        val userAnswer = answerStr.toInt()
        if (userAnswer == correctAnswer) {
            score += 10
            updateScore()
            Toast.makeText(this, "Congratulations, your answer is correct!", Toast.LENGTH_SHORT).show()
            editTextAnswer.setText("")
            generateQuestion()
        } else {
            Toast.makeText(this, "The answer was incorrect", Toast.LENGTH_SHORT).show()
            editTextAnswer.setText("")
            loseLife()
        }
    }

    private fun calculateAnswer(question: String): Int {
        val parts = question.split(" ").toTypedArray()
        val a = parts[0].toInt()
        val b = parts[2].toInt()
        val operator = parts[1]
        return when (operator) {
            "+" -> a + b
            "-" -> if (a >= b) a - b else b - a
            "*" -> a * b
            else -> if (b != 0) a / b else a
        }
    }

    private fun updateScore() {
        textScore.text = "Score: $score"
    }

    private fun updateLives() {
        textLives.text = "Lives: $lives"
    }

    private fun loseLife() {
        lives--
        updateLives()
        if (lives == 0) {
            endGame()
        } else {
            generateQuestion()
        }
    }

    private fun endGame() {
        timer?.cancel()
        val intent = Intent(this, EndActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
        finish()
    }
}

