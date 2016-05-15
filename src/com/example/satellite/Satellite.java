package com.example.satellite;

import com.example.satellitetracker.Point;

public class Satellite {
	
	private int id;
	private double longitude;
	private double latitude;
	
	private int x;
	private int y;
	
	public Satellite (int id, double longitude, double latitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;		
		
		x = 0;
		y = 0;
	}
	
	public String toString() {
		return "Satellite "+String.valueOf(id)+"    "+String.valueOf(longitude)+":"+String.valueOf(latitude)+"\n";
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point getPoint() {
		// Set -y because the y axe is inversed in screen landmark
		return new Point(x,-y);
	}

}
