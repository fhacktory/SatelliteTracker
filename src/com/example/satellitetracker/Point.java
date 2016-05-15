package com.example.satellitetracker;

public class Point {
	private int x;
	private int y;
	private int color;
	
	public Point(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getColor() {
		return color;
	}
}
