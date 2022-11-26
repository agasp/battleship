package com.agasp.battleship;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BattleshipApplication {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[] shipNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] shipSize = {5, 4, 3, 3, 2};
        char[][] plainTablePlayerOne = defaultTable();
        char[][] defaultTablePlayerOne = defaultTable();
        char[][] plainTablePlayerTwo = defaultTable();
        char[][] defaultTablePlayerTwo = defaultTable();
        int[] emptyArrayPlayerOne = new int[0];
        int[] emptyArrayPlayerTwo = new int[0];
        Ship[] fleetPlayerOne;
        Ship[] fleetPlayerTwo;
        System.out.println("Player 1, place your ships on the game field");
        printTable(plainTablePlayerOne);
        fleetPlayerOne = playerShip(shipNames, shipSize, scanner, emptyArrayPlayerOne, defaultTablePlayerOne, 1);
        //FIRE IF TRUE
        System.out.println("Press Enter and pass the move to another player ");
        scanner.nextLine();
        System.out.println("Player 1, place your ships on the game field");
        printTable(plainTablePlayerTwo);
        fleetPlayerTwo = playerShip(shipNames, shipSize, scanner, emptyArrayPlayerTwo, defaultTablePlayerTwo, 2);
        System.out.println("Press Enter and pass the move to another player ");
        scanner.nextLine();
        printTable(plainTablePlayerOne);
        System.out.println("--------------------");
        printTable(defaultTablePlayerOne);
        int hitCounterPlayerOne = 17;
        int hitCounterPlayerTwo = 17;
        while (hitCounterPlayerOne > 0 && hitCounterPlayerTwo > 0) {

            System.out.println("Player 1, it's your turn:");
            System.out.print(">");
            boolean check1 = fire(defaultTablePlayerTwo, fleetPlayerTwo, hitCounterPlayerOne, scanner, plainTablePlayerTwo);
            if (check1) {
                hitCounterPlayerOne--;
            }
            if (hitCounterPlayerOne > 0) {
                System.out.println("Press Enter and pass the move to another player ");
                scanner.nextLine();
                printTable(plainTablePlayerOne);
                System.out.println("--------------------");
                printTable(defaultTablePlayerTwo);
                System.out.println("Player 2, it's your turn:");
                System.out.print(">");
                boolean check2 = fire(defaultTablePlayerOne, fleetPlayerOne, hitCounterPlayerTwo, scanner, plainTablePlayerOne);
                if (check2) {
                    hitCounterPlayerTwo--;
                }
                if (hitCounterPlayerTwo >0) {
                    System.out.println("Press Enter and pass the move to another player ");
                    scanner.nextLine();
                    printTable(plainTablePlayerTwo);
                    System.out.println("--------------------");
                    printTable(defaultTablePlayerOne);
                }
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
        if (hitCounterPlayerOne == 0) {
            System.out.println("Player one wins");
        } else if (hitCounterPlayerTwo == 0) {
            System.out.println("Player two wins");
        }
    }

    public static int maxValue(int[] coordinates) {
        return max(coordinates[0], coordinates[1]);
    }

    public static int minValue(int[] coordinates) {
        return min(coordinates[0], coordinates[1]);
    }

    public static int[][] getCoordinates(String word) {
        String[] splitWord = word.split(" ");
        int[] numberCoordinates = new int[splitWord.length];
        int[] letterCoordinates = new int[splitWord.length];
        for (int n = 0; n < splitWord.length; n++) {
            numberCoordinates[n] = Integer.parseInt((splitWord[n].substring(1)));
            letterCoordinates[n] = splitWord[n].charAt(0) - 64;
        }
        Arrays.sort(letterCoordinates);
        Arrays.sort(numberCoordinates);
        return new int[][]{letterCoordinates, numberCoordinates};
    }

    public static boolean lengthCheck(int columnMax, int columnMin, int rowMax, int rowMin, int length, String name) {
        if ((rowMax - rowMin == length - 1)) {
            return true;
        } else if ((columnMax - columnMin == length - 1)) {
            return true;
        } else {
            System.out.printf("Error! Wrong length of the %s Try again: ", name);
            return false;
        }
    }

    public static boolean doesPlace(int columnMax, int columnMin, int rowMax, int rowMin) {
        if ((columnMax != columnMin) && (rowMax != rowMin)) {
            System.out.println("Error! Wrong ship location! Try again: ");
            return false;
        }
        return true;
    }

    public static void ShipPlacement(Ship ship, char[][] board, char shipSymbol) {
        if (ship.vertical) {
            for (int n = 0; n < board.length; n++) {
                for (int i = 0; i < board.length; i++) {
                    if (n >= ship.letterCoordinates[0] - 1 && n <= ship.letterCoordinates[1] - 1 && i == ship.numberCoordinates[0] - 1) {
                        board[n][i] = shipSymbol;
                    }
                }
            }
        } else {
            for (int n = 0; n < board.length; n++) {
                for (int i = 0; i < board.length; i++) {
                    if (ship.letterCoordinates[0] - 1 == n && i >= ship.numberCoordinates[0] - 1 && i <= ship.numberCoordinates[1] - 1) {
                        board[n][i] = shipSymbol;
                    }
                }
            }
        }
        printTable(board);
    }

    public static int[] array2Dto1D(int[][] array) {
        int index = 0;
        int[] array1D = new int[array.length * array[0].length];
        for (int[] value : array) {
            for (int i = 0; i < array[0].length; i++) {
                array1D[index] = value[i];
                index++;

            }
        }
        return array1D;
    }

    public static boolean compareArrays(int[] arrayOne, int[] arrayTwo) {
        for (int k : arrayTwo) {
            for (int j : arrayOne) {
                if (j == k && j >= 0) {
                    System.out.print("Error! You placed it too close to another one. Try again: ");

                    return true;
                }
            }
        }
        return false;
    }

    public static void printTable(char[][] playBoard) {
        System.out.println("\n");
        System.out.print("  ");
        for (int j : numberLegend()) {
            System.out.print(j + " ");
        }
        System.out.print("\n");
        for (int n = 0; n < letterLegend().length; n++) {
            System.out.print(letterLegend()[n] + " ");
            for (int i = 0; i < playBoard.length; i++) {
                System.out.print(playBoard[n][i] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    public static int[] arrayAdding(int[] array1, int[] array2) {
        int[] array3 = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, array3, 0, array1.length);
        System.arraycopy(array2, 0, array3, array1.length, array2.length);
        return array3;
    }

    public static boolean conditionsMeet(String word, int shipSize, String shipNames, int[] emptyArray) {
        boolean conditionsCheck = false;
        int[] NumbersIndex = getCoordinates(word)[1];
        int[] LettersIndex = getCoordinates(word)[0];

        if (doesPlace(maxValue(NumbersIndex), minValue(NumbersIndex),
                maxValue(LettersIndex), minValue(LettersIndex))) {

            if (lengthCheck(maxValue(NumbersIndex), minValue(NumbersIndex),
                    maxValue(LettersIndex), minValue(LettersIndex), shipSize, shipNames)) {
                Ship testShip = new Ship(shipNames, shipSize, LettersIndex, NumbersIndex, 0);
                conditionsCheck = !compareArrays(emptyArray, array2Dto1D(testShip.placementBoard()));

            }
        }
        return conditionsCheck;
    }

    public static char[] letterLegend() {
        char[] letterLegend = new char[10];
        for (int n = 0; n < letterLegend.length; n++) {
            letterLegend[n] = (char) ('A' + n);
        }
        return letterLegend;
    }

    public static int[] numberLegend() {
        int[] numberLegend = new int[10];
        for (int n = 0; n < numberLegend.length; n++) {
            numberLegend[n] = 1 + n;

        }
        return numberLegend;
    }

    public static char[][] defaultTable() {
        char[][] defaultTable = new char[10][10];
        for (char[] row : defaultTable
        ) {
            Arrays.fill(row, '~');
        }
        return defaultTable;
    }

    public static Ship[] playerShip(String[] shipNames, int[] shipSize, Scanner scanner, int[] emptyArray, char[][] defaultTable, int player) {
        Ship[] fleet = new Ship[5];
        for (int n = 0; n < 5; n++) {
            System.out.printf("Enter the coordinates of the %s (%d): ", shipNames[n], shipSize[n]);
            boolean allConditionTest = false;
            String Word = "";
            while (!allConditionTest) {
                Word = scanner.nextLine();
                allConditionTest = conditionsMeet(Word, shipSize[n], shipNames[n], emptyArray);
            }

            Ship ship;
            ship = new Ship(shipNames[n], shipSize[n], getCoordinates(Word)[0], getCoordinates(Word)[1], player);
            fleet[n] = ship;
            ShipPlacement(ship, defaultTable, 'O');
            emptyArray = arrayAdding(emptyArray, array2Dto1D(ship.placementBoardExtended()));
        }
        return fleet;
    }

    public static boolean coordinateCorrect(String word) {
        int[] coordinatesNumber = getCoordinates(word)[1];
        int[] coordinatesLetter = getCoordinates(word)[0];
        if ((coordinatesNumber[0] > 10) || (coordinatesLetter[0] > 10)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean fire(char[][] defaultTable, Ship[] fleet, int hitCounter, Scanner scanner, char[][] plainTableSecond) {
        boolean fired;
        String word = scanner.nextLine();
        boolean coordinatesCheck = false;
        while (!coordinatesCheck) {
            coordinatesCheck = coordinateCorrect(word);
            if (!coordinateCorrect(word)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                word = scanner.nextLine();
            }
        }
        int[] coordinatesNumber = getCoordinates(word)[1];
        int[] coordinatesLetter = getCoordinates(word)[0];

        if (defaultTable[coordinatesLetter[0] - 1][coordinatesNumber[0] - 1] == 'O') {
            defaultTable[coordinatesLetter[0] - 1][coordinatesNumber[0] - 1] = 'X';
            plainTableSecond[coordinatesLetter[0] - 1][coordinatesNumber[0] - 1] = 'X';
            for (Ship ship : fleet) {
                ship.test(getCoordinates(word), hitCounter);
            }
            fired = true;
        } else if (defaultTable[coordinatesLetter[0] - 1][coordinatesNumber[0] - 1] == 'X') {
            for (Ship ship : fleet) {
                ship.test(getCoordinates(word), hitCounter);
            }
            fired = false;
        } else {
            defaultTable[coordinatesLetter[0] - 1][coordinatesNumber[0] - 1] = 'M';
            plainTableSecond[coordinatesLetter[0] - 1][coordinatesNumber[0] - 1] = 'M';
            System.out.println("You missed");
            fired = false;
        }
        return fired;
    }

}



