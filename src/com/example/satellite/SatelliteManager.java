package com.example.satellite;

import android.util.Log;

public class SatelliteManager {
	
	private Sky sky;
	
	public SatelliteManager(Sky sky) {
		this.sky = sky;
		
		for(int i = 0; i< 90; i++) {
			sky.updateSatellite(1000+i, 0, i, "GP");
		}
		
		for(int i = -179; i< 179; i++) {
			sky.updateSatellite(2000+i, i, 0, "GP");
		}
	}
	
	public void NmeaParser(String nmea) {
		String tab[] = nmea.split(",");
		String country = (String) tab[0].subSequence(1, 3);
		String index = (String) tab[0].subSequence(3, 6);
		
		if (index.equals("GSV")) {

			try {
				int id = Integer.parseInt(tab[4]);
				double latitude = Integer.parseInt(tab[5]);
				double longitude = Integer.parseInt(tab[6]);
				if (longitude > 180)
					longitude = longitude - 360;
				
				sky.updateSatellite(id, longitude, latitude, country);
			} catch (NumberFormatException ex) {
				//ex.printStackTrace();
			}
		
		}
		
	}
	
	public void updateViewPosition(double alpha, double beta, double gamma) {
		
		// Map value in landscape orientation phone and convert radiant to degrees
		double view_longitude = (alpha-Math.PI/2)*180/Math.PI;
		if (view_longitude < -180)
			view_longitude = view_longitude + 360;
		double view_latitude = (-gamma-Math.PI/2)*180/Math.PI;
		if(view_latitude < -180)
			view_latitude=90;
		
		sky.updateView(view_longitude, view_latitude);
		
	}

}
