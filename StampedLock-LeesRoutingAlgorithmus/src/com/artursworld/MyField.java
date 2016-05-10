package com.artursworld;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class MyField {
	int [][] field;
	// Array von Logs (welchen lock brauche ich gerade)
	
	Queue<Point> queue = new LinkedList<Point>();
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
	
	/**
	 * set start location
	 * @param x
	 * @param y
	 */
	public void setStartLocation(int x, int y) {
		this.start.setLocation(x, y);
		field[x][y] = 0;
		addStartPointToQueue();
	}
	
	/**
	 * set finish location
	 * @param x
	 * @param y
	 */
	public void setFinishLocation(int x, int y) {
		this.finish.setLocation(x, y);
	}

	/**
	 * 
	 */
	public void runLeesRoutingAlgorithm() {	
		while(!queue.isEmpty()) {
			Point currentPoint = queue.poll();
			boolean isFinishPoint = currentPoint.getX() == finish.getX() && currentPoint.getY() == finish.getY();
			
			if(isFinishPoint) {
				System.out.println("finish reached! :)");
				break;
			}
			
			Point pointToCheck = getLeftPoint(currentPoint);
			if(pointToCheck != null){
				queue.add(pointToCheck);
			}
			
			printMatrix();
			System.out.println();
			
			pointToCheck = getTopPoint(currentPoint);
			if(pointToCheck != null){
				queue.add(pointToCheck);
			}
			
			printMatrix();
			System.out.println();
			
			pointToCheck = getRightPoint(currentPoint);
			if(pointToCheck != null){
				queue.add(pointToCheck);
			}
			
			printMatrix();
			System.out.println();
			
			pointToCheck = getBottomPoint(currentPoint);
			if(pointToCheck != null){
				queue.add(pointToCheck);
			}	
			
			printMatrix();
			System.out.println();
		}
	}
	
	/**
	 * add start point to queue
	 */
	private void addStartPointToQueue() {
		Point current = new Point(start);
		queue.add(current);
	}
	
	private MyPoint getLeftPoint(Point currentPoint) {
		if(currentPoint.getLocationX() == 0) {
			return null;
		}
		// get left
		int value = field[currentPoint.getLocationY()][currentPoint.getLocationX()-1];
		if(value != -1) {
			return null;
		}
		
		value = field[currentPoint.getLocationY()][currentPoint.getLocationX()];
		MyPoint ret = new MyPoint(currentPoint.getLocationX()-1, currentPoint.getLocationY(), value+1);
		field[currentPoint.getLocationY()][currentPoint.getLocationX()-1] = value+1;
		return ret;
	}
	
	private Point getRightPoint(Point currentPoint) {
		if(currentPoint.getX() == field[0].length-1) {
			return null;
		}
		
		int value = field[(int) currentPoint.getX()][(int) currentPoint.getY()]-1;
		if(value != -1) {
			return null;
		}
		
		value = field[(int) currentPoint.getX()][(int) currentPoint.getY()];
		Point ret = new Point();
		field[(int) currentPoint.getX()][(int) (currentPoint.getY()-1)] = value+1;
		return ret;
	}
	
	private Point getTopPoint(Point currentPoint) {
		if(currentPoint.getY() == 0) {
			return null;
		}
		
		int value = field[(int) currentPoint.getX()][(int) currentPoint.getY()]-1;
		if(value != -1) {
			return null;
		}
		
		value = field[(int) currentPoint.getX()][(int) currentPoint.getY()];
		Point ret = new Point();
		field[(int) currentPoint.getX()][(int) (currentPoint.getY()-1)] = value+1;
		return ret;
	}
	
	private Point getBottomPoint(Point currentPoint) {
		if(currentPoint.getY() == field.length)  {
			return null;
		}
		
		int value = field[(int) currentPoint.getX()][(int) currentPoint.getY()]-1;
		if(value != -1) {
			return null;
		}
		
		value = field[(int) currentPoint.getX()][(int) currentPoint.getY()];
		Point ret = new Point();
		field[(int) currentPoint.getX()][(int) (currentPoint.getY()-1)] = value+1;
		return ret;
	}
	
}
