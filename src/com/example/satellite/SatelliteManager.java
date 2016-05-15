package com.example.satellite;

import java.util.Vector;

import android.util.Log;

public class SatelliteManager implements SkyListened {
	
	private Sky sky = new Sky();

		
	private Vector<SkyListener> listeners = new Vector<SkyListener>();
	
	public SatelliteManager() {
	}
	
	public void NmeaParser(String nmea) {
		String tab[] = nmea.split(",");
		String index = (String) tab[0].subSequence(3, 6);
		if (index.equals("GSV")) {

			try {
				int id = Integer.parseInt(tab[4]);
				double latitude = Integer.parseInt(tab[5]);
				double longitude = Integer.parseInt(tab[6]);
				if (longitude > 180)
					longitude = longitude - 360;
				
				sky.updateSatellite(id, longitude, latitude);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		
		}
		
		updateListener(sky);
	}
	
	public void updateViewPosition(double alpha, double beta, double gamma) {
		
		// Map value in landscape orientation phone and convert radiant to degrees
		double view_longitude = (alpha+Math.PI/2)*180/Math.PI;
		if (view_longitude > 180.0)
			view_longitude = view_longitude - 360;
		double view_latitude = (-gamma-Math.PI/2)*180/Math.PI;
		if(view_latitude < -180)
			view_latitude=90;
		
		Log.v("Debug", "Position de la vue \n"+view_longitude+"\n"+view_latitude);
		sky.updateView(view_longitude, view_latitude);
		updateListener(sky);
		
	}
	
	@Override
	public void addListener(SkyListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public void deleteListener(SkyListener listener) {
		listeners.remove(listener);
		
	}

	@Override
	public void updateListener(Sky sky) {
		for(SkyListener listener : listeners)
			listener.updateSky(sky);
		
	}

}
