package com.example.distributedtictactoe;

public class Game {
    public String currentTurn;
    public int[][] board;
    public String jogadorX;
    public String jogadorO;

    public Game() {
    }

    public Game(String jogadorX, String jogadorO) {
        this.currentTurn = "jogadorX";
        this.board = new int[3][3];
        this.jogadorX = jogadorX;
        this.jogadorO = jogadorO;
    }
}
