package com.example.distributedtictactoe

data class Game(
    val board: MutableList<String> = MutableList(9) { "" },
    var jogadorX: String = "jogadorX",
    var jogadorO: String = "jogador0",
    var turn: String = "X"
)