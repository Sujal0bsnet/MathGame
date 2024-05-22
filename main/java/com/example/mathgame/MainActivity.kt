package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.buttonAddition).setOnClickListener { view: View? ->
            startGame(
                "addition"
            )
        }
        findViewById<View>(R.id.buttonSubtraction).setOnClickListener { view: View? ->
            startGame(
                "subtraction"
            )
        }
        findViewById<View>(R.id.buttonMultiplication).setOnClickListener { view: View? ->
            startGame(
                "multiplication"
            )
        }
        findViewById<View>(R.id.buttonDivision).setOnClickListener { view: View? ->
            startGame(
                "division"
            )
        }
    }

    private fun startGame(operation: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("operation", operation)
        startActivity(intent)
    }
}
