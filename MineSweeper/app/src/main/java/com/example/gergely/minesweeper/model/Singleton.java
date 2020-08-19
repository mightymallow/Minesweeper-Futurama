package com.example.gergely.minesweeper.model;

public class Singleton {

    private int numberOfRows;
    private int numberOfColumns;
    private int numberOfMines;


    //Singleton support
    private static Singleton instance;
    private Singleton(){}
    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    //typical getters and setters for the 3 private fields are found below (nothing special)

    public int getNumberOfRows(){
        return numberOfRows;
    }

    public int getNumberOfColumns(){
        return numberOfColumns;
    }

    public int getNumberOfMines(){
        return numberOfMines;
    }

    public void setNumberOfRows(int theNumber){
        numberOfRows = theNumber;
    }

    public void setNumberOfColumns(int theNumber){
        numberOfColumns = theNumber;
    }

    public void setNumberOfMines(int theNumber){
        numberOfMines = theNumber;
    }
}
