package com.example.satellite;

import android.util.Log;

public class SkyControler {
	
	private Sky sky;
	
	// Variable to perform a low-pass filter
	private int length = 2;
	private double alphaTab[] = new double[length];
	private double gammaTab[] = new double[length];
	private int iteration = length-1;
	
	public SkyControler(Sky sky) {
		this.sky = sky;
		
		for(int i = 0; i< 90; i++) {
			sky.updateSatellite(1000+i, 0, i, "MOCK");
		}
		
		for(int i = -179; i< 179; i++) {
			sky.updateSatellite(2000+i, i, 0, "MOCK");
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
		
		if(iteration < 0) {
			// compute a mean of tabs
			// restart counter and tab
			double alphaMean = 0;
			for(double d : alphaTab)
				alphaMean += d;
			
			double gammaMean = 0;
			for(double d: gammaTab)
				gammaMean +=d;
					
			alpha = alphaMean/length;
			gamma = gammaMean/length;
			alphaTab = new double[length];
			gammaTab =  new double[length];
			iteration = length-1;
			
		
			// Map value in landscape orientation phone and convert radiant to degrees
			double view_longitude = (alpha-Math.PI/2)*180/Math.PI;
			if (view_longitude < -180)
				view_longitude = view_longitude + 360;
			double view_latitude = (-gamma-Math.PI/2)*180/Math.PI;
			if(view_latitude < -180)
				view_latitude=90;
		
			sky.updateView(view_longitude, view_latitude);
			
		}
		
		else {
			alphaTab[iteration] = alpha;
			gammaTab[iteration] = gamma;
			iteration--;
		}
		
	}

}
