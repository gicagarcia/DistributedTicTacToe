package com.example.distributedtictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.distributedtictactoe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private val gab: ActivityGameBinding by lazy{
        ActivityGameBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }
}