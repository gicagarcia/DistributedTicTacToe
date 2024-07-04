package com.example.distributedtictactoe

data class Game(
    val id: String = "",
    val board: List<MutableList<String>> = List(3) { MutableList(3) { "" } },
    var jogadorX: String = "jogadorX",
    var jogadorO: String = "jogador0",
    var turn: String = "jogadorX"
)