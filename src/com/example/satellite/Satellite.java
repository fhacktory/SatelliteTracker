package com.example.satellite;

import android.graphics.Color;

import com.example.satellitetracker.Point;

public class Satellite {
	
	private int id;
	private double longitude;
	private double latitude;
	private int country_color;
	
	private int x;
	private int y;
	
	public Satellite (int id, double longitude, double latitude, String country) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		
		// GP for GPS : American
		if(country.equals("GP"))
			country_color = Color.WHITE;
		// GN for GLONASS: Russian
		if(country.equals("GN"))
			country_color = Color.GREEN;
		// BD for Beidou or COMPASS : China
		if(country.equals("BD"))
			country_color = Color.RED;
		// GL for GALILEO : European
		if(country.equals("GL"))
			country_color = Color.BLUE;
		
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
		return new Point(x,-y, country_color);
	}

}
