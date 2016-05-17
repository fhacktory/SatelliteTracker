package com.example.satellitetracker;

import android.app.Activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.example.satellite.Sky;
import com.example.satellite.SkyControler;
import com.example.satellite.SkyListener;

/**
 * Main program activity. It's the View component in MVC patern. Listen rotation sensor and NMEA sequences by GPS sensor.
 * @author Francois Jolain
 *
 */
public class MainActivity extends Activity implements GpsStatus.NmeaListener, SkyListener {
	
	private LocationManager lm;
	private SkyControler controler;
	
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private MySensorListener mySensorListener;
	
	private DrawView draw;
	
	private float rotation[];
	private float accelerometerValues[];
	private float magnetometerValues[];
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Use landscape orientation
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
				
		// Fetch display resolution to be used by DrawView
		Display display = getWindowManager().getDefaultDisplay();
		android.graphics.Point size = new android.graphics.Point();
		display.getSize(size);
		draw = new DrawView(this, size.x, size.y);
		setContentView(draw);
		
		
		// Setup location manager
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000,10,new MyLocationListener());
	    //final Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    lm.addNmeaListener(this);

	    // Setup sky and its controler
	    Sky sky = new Sky();
	    controler = new SkyControler(sky);
	    sky.addListener(this);
	    
	    // Setup Sensor
	    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	    mySensorListener = new MySensorListener();
	    sensorManager.registerListener(mySensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
	    sensorManager.registerListener(mySensorListener, magnetometer, SensorManager.SENSOR_DELAY_UI);
	    
	}
	
	/**
	 * Implementation from GpsStatus.NmeaListener
	 * Call back when a new NMEA sequence is received
	 */
	@Override
	public void onNmeaReceived(long timestamp, String nmea) {
		// Give the sentence to the controler
		controler.NmeaParser(nmea);
		
	}

	/**
	 * Implementation from SkyListener.
	 * Call back when satellite position from earth and satellite position from screen rotation changed.
	 */
	@Override
	public void updateSky(Sky sky) {
		// Ask a to draw a new screen with new points
		draw.setPoints(sky.getPoints());
	}
	
	
	@Override
	protected void onPause() {
        super.onPause();
        // Stop to listening rotation sensor
        sensorManager.unregisterListener(mySensorListener);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		// Restart listening rotation sensor
		sensorManager.registerListener(mySensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
	    sensorManager.registerListener(mySensorListener, magnetometer, SensorManager.SENSOR_DELAY_UI);
	}
	
	
	
	
	
	/**
	 * Implements SensorEventListener.
	 * Used to gathering new rotation sensor values.
	 * @author francois
	 *
	 */
	private class MySensorListener implements SensorEventListener {

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent se) {
			
			
			// Filter event by sensor actived
			if(se.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
				accelerometerValues = se.values;
			if(se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
				magnetometerValues = se.values;
			
			// Update rotation of the screen's phone
			rotation = new float[3];
			float[] R = new float[9];
			float[] I = new float[9];
			if (accelerometerValues != null && magnetometerValues != null) {
				SensorManager.getRotationMatrix(R, I, accelerometerValues, magnetometerValues);
				SensorManager.getOrientation(R, rotation);

				// Send new rotation to the controler
				controler.updateViewPosition(rotation[0], rotation[1], rotation[2]);
			}
			
		}
		
	
	
}
	
	
	/**
	 * This class is necessary to be updated from new NMEA sequences. 
	 * Not methods are used here.
	 * @author François Jolain
	 *
	 */
	private class MyLocationListener implements android.location.LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// nothing
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// nothing
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// nothing
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// nothing
			
		}
		
	}
	

	
}
