package com.example.gergely.minesweeper.model;

import java.util.Random;

public class GameLogic {

    private int minesFound;
    private int scansUsed;
    private boolean[][] theMinefield;
    private boolean[][] hasBeenClicked;
    private boolean[][] hasBeenUsedToScan;

    //creates the minefield randomly
    public void createMinefield (int numberOfRows, int numberOfColumns, int numberOfMines){
        int numberOfMinesPlaced = 0;
        minesFound = 0;
        scansUsed = 0;
        theMinefield = new boolean[numberOfRows][numberOfColumns];
        hasBeenClicked = new boolean[numberOfRows][numberOfColumns];
        hasBeenUsedToScan = new boolean [numberOfRows][numberOfColumns];

        Random random = new Random();
        while (numberOfMinesPlaced < numberOfMines){
            int rows = random.nextInt(numberOfRows);
            int columns = random.nextInt(numberOfColumns);
            if (!theMinefield[rows][columns]) {
                theMinefield[rows][columns] = true;
                numberOfMinesPlaced++;
            }
        }
    }

    //checks if a spot is a mine
    public boolean checkSpot(int row, int column){
        if (theMinefield[row][column] == false) {
            return false;
        } else {
            minesFound++;
            return true;
        }
    }

    //checks if a spot has been clicked yet
    public boolean checkHasBeenClicked(int row, int column){
        if(hasBeenClicked[row][column] == false){
            return false;
        } else {
            return true;
        }
    }

    //checks if a spot has performed a scan yet
    public boolean checkHasBeenUsedToScan(int row, int column){
        if(hasBeenUsedToScan[row][column] == false){
            return false;
        } else {
            return true;
        }
    }

    //performs a scan to return how many mines are hidden in the same row/column
    public int performScan(int row, int column, int maxRows, int maxColumns){
        int mineScan = 0;

        for (int i = 0; i < maxRows; i++){
            if(theMinefield[i][column] == true && checkHasBeenClicked(i, column) == false){
                mineScan++;
            }
        }

        for (int i = 0; i < maxColumns; i++){
            if(theMinefield[row][i] == true && checkHasBeenClicked(row,i) == false){
                mineScan++;
            }
        }
        scansUsed++;
        return mineScan;
    }

    //typical getters and setters (nothing special);

    public int getMinesFound(){
        return minesFound;
    }

    public int getScansUsed(){
        return scansUsed;
    }

    public void setHasBeenClicked(int row, int column){
        hasBeenClicked[row][column] = true;
    }

    public void setHasBeenUsedToScan(int row, int column){
        hasBeenUsedToScan[row][column] = true;
    }


}
