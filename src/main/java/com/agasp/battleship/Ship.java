package com.agasp.battleship;

import java.util.Arrays;

public class Ship {
    String shipName;
    int[] letterCoordinates;
    int[] numberCoordinates;
    int length;
    boolean horizontal;
    boolean vertical;
    int health;
    int player;
    //static int  maxHealth;


    public Ship(String shipName, int length, int[] letterCoordinates, int[] numberCoordinates, int player) {
        this.shipName = shipName;
        this.length = length;
        this.letterCoordinates = letterCoordinates;
        this.numberCoordinates = numberCoordinates;
        if (letterCoordinates[0] == letterCoordinates[1]) {
            this.horizontal = true;
            this.vertical = false;

        } else if (numberCoordinates[0] == numberCoordinates[1]) {
            this.horizontal = false;
            this.vertical = true;
        }
        this.health = length;
        this.player = player;
    }

    public void test(int[][] bombCoordinates, int counter) {
        int bombField = Integer.parseInt(bombCoordinates[0][0] + String.valueOf(bombCoordinates[1][0]));
        for (int n = 0; n < placementBoard().length; n++) {
            for (int i = 0; i < placementBoard()[0].length; i++) {
                if (placementBoard()[n][i] == bombField && health > 0 && placementBoardExtended()[n][i] !=0) {
                    if (placementBoard()[n][i] != 0) {
                        health--;
                        placementBoard()[n][i] = 0;
                    }
                    if (health > 0 && counter > 1) {
                        System.out.println("You hit a ship!");
                    }
                }
            }
        }
        if (health == 0 && counter > 1) {
            System.out.println("You sank a ship! Specify a new target:");
            health = -1;
        }
    }

    public int[][] placementBoard() {
        int[][] board = new int[0][0];
        if (this.horizontal) {
            board = new int[1][this.length];
            for (int n = 0; n < this.length; n++) {
                board[0][n] = Integer.parseInt(letterCoordinates[0] + String.valueOf(numberCoordinates[0] + n));
            }


        } else if (vertical) {
            board = new int[this.length][1];
            for (int n = 0; n < this.length; n++) {
                board[n][0] = Integer.parseInt(letterCoordinates[0] + n + String.valueOf(numberCoordinates[0]));

            }

        }
        return board;
    }

    public int[][] placementBoardExtended() {
        int[][] board = new int[0][0];

        if (this.horizontal) {
            board = new int[3][length + 2];
            for (int n = 0; n < this.length + 2; n++) {
                board[0][n] = Integer.parseInt(String.valueOf(letterCoordinates[0] - 1) + (numberCoordinates[0] + n - 1));
                board[1][n] = Integer.parseInt(String.valueOf(letterCoordinates[0]) + (numberCoordinates[0] + n - 1));
                board[2][n] = Integer.parseInt(String.valueOf(letterCoordinates[0] + 1) + (numberCoordinates[0] + n - 1));
            }
            board[0][0] = -69;
            board[0][length + 1] = -69;
            board[2][length + 1] = -69;
            board[2][0] = -69;


        } else if (vertical) {
            board = new int[length + 2][3];
            for (int n = 0; n < this.length + 2; n++) {
                board[n][0] = Integer.parseInt(letterCoordinates[0] + n - 1 + String.valueOf(numberCoordinates[0] - 1));
                board[n][1] = Integer.parseInt(letterCoordinates[0] + n - 1 + String.valueOf(numberCoordinates[0]));
                board[n][2] = Integer.parseInt(letterCoordinates[0] + n - 1 + String.valueOf(numberCoordinates[0] + 1));
            }
            board[0][0] = -69;
            board[length + 1][0] = -69;
            board[length + 1][2] = -69;
            board[0][2] = -69;


        }
        return board;
    }


}

