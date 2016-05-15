package com.example.satellite;

import java.util.Vector;

import android.util.Log;

import com.example.satellitetracker.Point;
import com.example.socket.ConnectionSocket;


public class Sky {

    private Vector<Satellite> sat = new Vector<Satellite>();
    private Vector<Point> points = new Vector<Point>();

    public Sky() {
    }

    public boolean updateSatellite(int id, double longitude, double latitude) {
      	    	
        for(int i= 0; i<sat.size(); i++) {
        	
        	if(i == 0)
        	if(sat.get(i).getId()==id) {
        		sat.get(i).setLatitude(latitude);
        		sat.get(i).setLongitude(longitude);
        		return true;
        	}
        }
        
        sat.add(new Satellite(id, longitude, latitude));
        return false;
    }
    
    public void updateView(double longitude, double latitude) {
    	
    	for(Satellite s : sat) {
    		
    			
    		// Compute satellite coordinate in the main situation
    		int x = (int) (s.getLongitude()-longitude);//(longitude_max-longitude)/2560 );
    		int y = (int) (s.getLatitude()-latitude);//(latitude-latitude_max)/1440 );
    			
    		// Ajust x in specific situation
    		// If screen landmark is near border +180|-180, in the positive side
    		if(longitude > 0 && s.getLongitude()<0)
    			x = (int) ((180-longitude)+(180-s.getLatitude()));
    		
    		s.setXY(x, y);
    		
    	}
    	
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


}
