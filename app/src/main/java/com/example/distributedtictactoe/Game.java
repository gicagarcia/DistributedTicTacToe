package com.example.distributedtictactoe;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public String turn;
    public List<List<Integer>> board;
    public String jogadorX;
    public String jogadorO;

    public Game() {
        this.turn = "jogadorX";
        this.board = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(0);
            }
            this.board.add(row);
        }
        this.jogadorX = "jogadorX";
        this.jogadorO = "jogadorO";
    }
}
