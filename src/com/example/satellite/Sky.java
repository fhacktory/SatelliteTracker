package com.example.satellite;

import java.util.Vector;

import com.example.satellitetracker.Point;

/**
 * This class manager Satellite on sky and compute Satellite coordinate on screen's phone.
 * It's build on a Observer pattern
 * @author François Jolain
 * @see SkyListener
 * @see SkyListned
 *
 */
public class Sky implements SkyListened{

    private Vector<Satellite> sat = new Vector<Satellite>();
	private Vector<SkyListener> listeners = new Vector<SkyListener>();


    public Sky() {
    }

    /**
     * Update the satellite collection with the new satellite informations
     * @param id satellite id
     * @param longitude satellite longitude from -180 to 180 with south at 0
     * @param latitude latitude satellite from 0 to 90 with equator at 0
     * @param country two letters country name code
     * @return
     */
    public boolean updateSatellite(int id, double longitude, double latitude, String country) {
      	
    	// Check the satellite collection
        for(int i= 0; i<sat.size(); i++) {
        	
        	// If a satellite share the same id, sky update values of this satellite
        	if(sat.get(i).getId()==id) {
        		sat.get(i).setLatitude(latitude);
        		sat.get(i).setLongitude(longitude);
        		updateListener(this);
        		return true;
        	}
        }
        
        // Else no satellite is found. Satellite is added in collection
        sat.add(new Satellite(id, longitude, latitude, country));
        
        updateListener(this);
        return false;
    }
    
    /**
     * Update screen orientation pointed
     * @param longitude longitude of the point on top at left on the screen
     * @param latitude latitude of the point on the top at left on the screen
     */
    public void updateView(double longitude, double latitude) {
    	
    	for(Satellite s : sat) {
    		
    		// Compute satellite coordinate in the main situation
    		double x = (s.getLongitude()-longitude);
    		double y = (s.getLatitude()-latitude);
    			
    		// Ajust x in specific situation
    		if(longitude > 0 && s.getLongitude()<0)
    			x = (int) ((180-longitude)+(180+s.getLongitude()));
    		
    		s.setXY(x, y);
    		
    	}
    	
    	updateListener(this);
    }
    
    /**
     * Return the current collection of points
     * @return collection of points
     */
    public Vector<Point> getPoints() {
    	
    	// Collection point is build only when program ask it
    	Vector<Point>points = new Vector<Point>();
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
