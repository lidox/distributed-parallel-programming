package com.artursworld;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class MyField {
	int [][] field;
	// Array von Logs (welchen lock brauche ich gerade)
	
	Queue<Integer> q = new LinkedList<Integer>();
	Point start = new Point();
	Point finish = new Point();
	
	/**
	 * init field by field size
	 * @param fieldSize
	 */
	public MyField(int fieldSize) {
		this.field = new int [fieldSize][fieldSize];
		this.initMatrix();
	}
	
	/**
	 * Print the matrix to console
	 */
	public void printMatrix() {
		String matrix = "";
		for (int yRichtung = 0; yRichtung < field.length; yRichtung++) {
			if (field[0].length > 0) {
				for (int xRichtung = 0; xRichtung < field[0].length; xRichtung++) {
					matrix += field[yRichtung][xRichtung] + "\t"; 
				}
				matrix += "\n";
			}
		}
		System.out.println(matrix); 
	}
	
	/**
	 * set all values to -1
	 */
	private void initMatrix() {
		for (int yRichtung = 0; yRichtung < field.length; yRichtung++) {
			if (field[0].length > 0) {
				for (int xRichtung = 0; xRichtung < field[0].length; xRichtung++) {
					field[yRichtung][xRichtung] = -1;
				}
			}
		}
	}
	
}
