package com.example.distributedtictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.distributedtictactoe.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        amb.toolbarIn.exitButton.setOnClickListener{
            finish()
        }

        database = FirebaseDatabase.getInstance().getReference("games")

        amb.btnCreate.setOnClickListener{
            createGame()
        }

        amb.btnEnter.setOnClickListener{
            //enterGame()
        }
    }

    private fun createGame(){
        val game = Game()
        val newGame = database.child("games").push()
        newGame.setValue(game).addOnSuccessListener {
            val id = newGame.key
            Toast.makeText(this, "ID: $id", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("gameId", id)
            intent.putExtra("player", "jogadorX")
            startActivity(intent)
        }
    }
}