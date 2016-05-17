package com.example.satellite;

import android.util.Log;
/**
 * This class is the bridge between raw values returned by sensor and Sky interface.
 * It's the controler part in MVC.
 * @author François Jolain
 * @see Sky
 *
 */
public class SkyControler {
	
	private Sky sky;
	
	// Variable to perform a low-pass filter
	private int length = 2;
	private double alphaTab[] = new double[length];
	private double gammaTab[] = new double[length];
	private int iteration = length-1;
	
	public SkyControler(Sky sky) {
		this.sky = sky;
		
		// Draw longitude and latitude landmark in screen by adding mocked satellites on sky.
		for(int i = 0; i< 90; i++) {
			sky.updateSatellite(1000+i, 0, i, "MOCK");
		}
		
		for(int i = -179; i< 179; i++) {
			sky.updateSatellite(2000+i, i, 0, "MOCK");
		}
	}
	
	/**
	 * Parser NMEA sequence to fetch informations about satellite
	 * @param nmea
	 */
	public void NmeaParser(String nmea) {
		// Split string in tab
		String tab[] = nmea.split(",");
		// Fetch country by the two first letters in first item
		String country = (String) tab[0].subSequence(1, 3);
		// Fetch index command by the following letters in first item
		String index = (String) tab[0].subSequence(3, 6);
		
		// Sky just care about GSV command
		if (index.equals("GSV")) {

			try {
				// Fetch satellite information in tab 
				int id = Integer.parseInt(tab[4]);
				double latitude = Integer.parseInt(tab[5]);
				double longitude = Integer.parseInt(tab[6]);
				
				// Redress longitude interval from [0, 360] --> [-180, 180]
				if (longitude > 180)
					longitude = longitude - 360;
				
				// Update sky with new satellites coordinate
				sky.updateSatellite(id, longitude, latitude, country);
			} catch (NumberFormatException ex) {
				//ex.printStackTrace();
			}
		
		}
		
	}
	
	/**
	 * compute the screen pointed direction from values sensor
	 * @param alpha angle on Z axis
	 * @param beta angle on X axis
	 * @param gamma angle on Y axis
	 */
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
		
			// Update sky by the pointed direction
			sky.updateView(view_longitude, view_latitude);
			
		}
		
		else {
			alphaTab[iteration] = alpha;
			gammaTab[iteration] = gamma;
			iteration--;
		}
		
	}

}
