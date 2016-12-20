package com.artursworld;

/**
 * Zelle bestehend aus x- und y-Koordinaten. Jede Zelle ist als besucht markiert, oder nicht und hat einen Wert
 */
public class Cell {

    private int x, y;
    private boolean visited;
    private int value;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.visited = false;
        this.value = -1;
    }

    public void setVisited(boolean value){
        this.visited = value;
    }

    public boolean isVisited(){
        return visited;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}

