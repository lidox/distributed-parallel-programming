package com.artursworld;

public class StampedLockMain {

	public static void main(String[] args) {
		MyField field = new MyField(10);
		
		// set start and finish
		field.setStartLocation(3, 2);
		field.setFinishLocation(6, 6);
		
		field.runLeesRoutingAlgorithm();
		
		field.printMatrix();
	}

}
