package com.example.satellite;

import java.util.Vector;

import android.util.Log;

import com.example.satellitetracker.Point;
import com.example.socket.ConnectionSocket;


public class Sky implements SkyListened{

    private Vector<Satellite> sat = new Vector<Satellite>();
    private Vector<Point> points = new Vector<Point>();
	private Vector<SkyListener> listeners = new Vector<SkyListener>();


    public Sky() {
    }

    public boolean updateSatellite(int id, double longitude, double latitude, String country) {
      	    	
        for(int i= 0; i<sat.size(); i++) {
        	
        	
        	if(sat.get(i).getId()==id) {
        		sat.get(i).setLatitude(latitude);
        		sat.get(i).setLongitude(longitude);
        		updateListener(this);
        		return true;
        	}
        }
        
        sat.add(new Satellite(id, longitude, latitude, country));
        
        updateListener(this);
        return false;
    }
    
    public void updateView(double longitude, double latitude) {
    	
    	for(Satellite s : sat) {
    		
    		// Compute satellite coordinate in the main situation
    		double x = (s.getLongitude()-longitude);
    		double y = (s.getLatitude()-latitude);
    			
    		// Ajust x in specific situation
    		// If screen landmark is near border +180|-180, in the positive side
    		if(longitude > 0 && s.getLongitude()<0)
    			x = (int) ((180-longitude)+(180+s.getLongitude()));
    		
    		s.setXY(x, y);
    		
    	}
    	
    	updateListener(this);
    }
        
    public Vector<Point> getPoints() {
    	points = new Vector<Point>();
    	for(Satellite s : sat)
    		points.add(s.getPoint());
    	
    	return points;
    }
    
    
    public String toString() {
		String str = "";
		for(Satellite satellite : sat)
			str += satellite.toString();
		return str;
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
