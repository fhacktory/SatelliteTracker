package com.example.satellite;

import android.graphics.Color;
import android.util.Log;

import com.example.satellitetracker.Point;

public class Satellite {
	
	private int id;
	private double longitude;
	private double latitude;
	private int country_color;
	
	private double x;
	private double y;
	private int point_size = 20; // default size for satellite
	
	public Satellite (int id, double longitude, double latitude, String country) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		
		// GP for GPS : American
		if(country.equals("GP"))
			country_color = Color.WHITE;
		// GL for GLONASS: Russian
		else if(country.equals("GL"))
			country_color = Color.GREEN;
		// BD or GB for Beidou or COMPASS : China
		else if(country.equals("BD") || country.equals("GB"))
			country_color = Color.RED;
		// GA for GALILEO : European
		else if(country.equals("GA"))
			country_color = Color.BLUE;
		// Special country that doesn't exist, just to display a mock satellite on sky
		else if(country.equals("MOCK")) {
			country_color = Color.rgb(100, 100, 100); // light gray color
			point_size = 10; // Its point is smaller than true satellite
		}
	
		
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
	
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point getPoint() {
		// Set -y because the y axe is inversed in screen landmark
		return new Point(x,-y, country_color, point_size);
	}

}
