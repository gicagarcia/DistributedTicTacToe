package com.example.distributedtictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.distributedtictactoe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var gab: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }
}