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

        database = FirebaseDatabase.getInstance().reference

        amb.btnCreate.setOnClickListener{
            createGame()
        }

        amb.btnEnter.setOnClickListener{
            showEnterGameDialog()
        }
    }

    private fun createGame(){
        val game = database.child("games").push()
        game.setValue(Game())

        val id = game.key
        if(id != null){

            val intent = Intent(this@MainActivity, GameActivity::class.java).apply {
                putExtra("id", id)
            }
            startActivity(intent)
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
        database.child("games").child(gameId).get().addOnSuccessListener {
            if (it.exists()) {
                val intent = Intent(this, GameActivity::class.java).apply {
                    putExtra("GAME_ID", gameId)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Game ID does not exist", Toast.LENGTH_SHORT).show()
            }
        }
    }
}