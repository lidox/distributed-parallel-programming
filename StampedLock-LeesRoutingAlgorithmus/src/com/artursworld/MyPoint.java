package com.artursworld;

import java.awt.Point;

public class MyPoint {
	
	private Point point = new Point();
	private int value;
	
	public MyPoint(MyPoint p, int value) {
		this.point = new Point(p.getLocationX(), p.getLocationY());
		this.value = value;
	}
	
	public MyPoint(int xLocation, int yLocation, int value) {
		this.point.setLocation(xLocation, yLocation);
		this.value = value;
	}
	
	public MyPoint() {
		this.point.setLocation(-1, -1);
		this.value = -1;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public int getLocationX() {
		return this.point.x;
	}
	
	public int getLocationY() {
		return this.point.y;
	}
	
	public void setLocation(int x, int y) {
		this.point.setLocation(x, y);
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
