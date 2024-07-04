package com.example.distributedtictactoe;

public class Game {
    public String turn;
    public int[][] board;
    public String jogadorX;
    public String jogadorO;

    public Game() {
    }

    public Game(String jogadorX, String jogadorO) {
        this.turn = "jogadorX";
        this.board = new int[3][3];
        this.jogadorX = jogadorX;
        this.jogadorO = jogadorO;
    }
}
