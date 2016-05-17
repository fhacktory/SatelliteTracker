package com.example.satellitetracker;

/**
 * Class to record in a single place all informations necessary to print a satellite on screen.
 * Coordinates are screen coordinate with Y axis inverted.
 * @author François Jolain
 *
 */
public class Point {
	private double x;
	private double y;
	private int color;
	private int size;
	
	public Point(double x, double y, int color, int size) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getSize() {
		return size;
	}
}
