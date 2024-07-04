package com.example.distributedtictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        database = FirebaseDatabase.getInstance().reference.child("games")

        amb.btnCreate.setOnClickListener{
            createGame()
        }

        amb.btnEnter.setOnClickListener{
            showEnterGameDialog()
        }
    }

    private fun createGame(){
        val game = Game()
        val newGame = database.push().key ?: ""
        database.child(newGame).setValue(game).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this, "ID: $newGame", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEnterGameDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Game ID")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("Enter") { dialog, _ ->
            val gameId = input.text.toString()
            enterGame(gameId)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun enterGame(gameId: String) {
        database.child(gameId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("gameId", gameId)
                intent.putExtra("player", "jogadorO")
                startActivity(intent)
            } else {
                Toast.makeText(this, "Game ID not found", Toast.LENGTH_LONG).show()
            }
        }
    }
}