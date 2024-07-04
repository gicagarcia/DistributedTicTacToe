package com.example.distributedtictactoe

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.distributedtictactoe.databinding.ActivityGameBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GameActivity : AppCompatActivity() {
    private val gab: ActivityGameBinding by lazy{
        ActivityGameBinding.inflate(layoutInflater)
    }

    private lateinit var database: DatabaseReference
    private lateinit var gameId: String
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(gab.root)

        setSupportActionBar(gab.toolbarIn.toolbar)
        gab.toolbarIn.exitButton.setOnClickListener{
            finish()
        }

        database = FirebaseDatabase.getInstance().reference.child("games")
        gameId = intent.getStringExtra("id")!!

        gab.tvGameId.text = gameId
        gab.btnCopyGameId.setOnClickListener{
            val copy = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Game ID", gameId)
            copy.setPrimaryClip(data)
        }

        setBoard()
        loadGame()
    }

    private fun loadGame(){
        database.child(gameId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(Game::class.java)?.let {
                    game = it
                    updateBoard(game.board)
                    checkGame()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setBoard() {
        val boardButtons = listOf(
            gab.button00,
            gab.button01,
            gab.button02,
            gab.button10,
            gab.button11,
            gab.button12,
            gab.button20,
            gab.button21,
            gab.button22
        )

        for (i in boardButtons.indices) {
            boardButtons[i].setOnClickListener {
                makeMove(i)
            }
        }
    }

    private fun makeMove(position: Int) {
        if (game.board[position].isEmpty()) {
            val newBoard = game.board.toMutableList()
            newBoard[position] = game.turn
            val nextPlayer = if (game.turn == "X") "O" else "X"
            val updatedGame = game.copy(board = newBoard, turn = nextPlayer)
            database.child(gameId).setValue(updatedGame).addOnSuccessListener {
                loadGame()
            }
        }else{
            Toast.makeText(this, "Already has a move", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateBoard(board: List<String>) {
        val boardButtons = listOf(
            gab.button00,
            gab.button01,
            gab.button02,
            gab.button10,
            gab.button11,
            gab.button12,
            gab.button20,
            gab.button21,
            gab.button22
        )

        for (i in board.indices) {
            boardButtons[i].text = board[i]
        }
    }

    private fun checkGame() {
        val board = game.board


        val winningPositions = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),

            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),

            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        for (positions in winningPositions) {
            val (a, b, c) = positions
            if (board[a].isNotEmpty() && board[a] == board[b] && board[b] == board[c]) {
                Toast.makeText(this, "${board[a]} wins!", Toast.LENGTH_LONG).show()
                return
            }
        }

        if (board.all { it.isNotEmpty() }) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show()
        }
    }

}