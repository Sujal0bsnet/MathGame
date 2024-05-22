package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EndActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
        val score = intent.getIntExtra("score", 0)
        val textFinalScore = findViewById<TextView>(R.id.textFinalScore)
        textFinalScore.text = "Your Score: $score"
        findViewById<View>(R.id.buttonPlayAgain).setOnClickListener { view: View? ->
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
        findViewById<View>(R.id.buttonExit).setOnClickListener { view: View? -> finish() }
    }
}
