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
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.satellite.SatelliteManager;
import com.example.satellite.Sky;
import com.example.satellite.SkyListener;

public class MainActivity extends Activity implements GpsStatus.NmeaListener, SkyListener {
	
	private LocationManager lm;
	private SatelliteManager manager;
	
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
		// Use landscape orientation
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
				
		super.onCreate(savedInstanceState);
		draw = new DrawView(this);
		setContentView(draw);
		
		
		// Setup location manager
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000,10,new MyLocationListener());
	    //final Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    lm.addNmeaListener(this);

	    // Setup satellite manager
	    Sky sky = new Sky();
	    manager = new SatelliteManager(sky);
	    sky.addListener(this);
	    
	    // Setup Sensor
	    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	    mySensorListener = new MySensorListener();
	    sensorManager.registerListener(mySensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
	    sensorManager.registerListener(mySensorListener, magnetometer, SensorManager.SENSOR_DELAY_UI);
	    
	}
	
	@Override
	public void onNmeaReceived(long timestamp, String nmea) {
		manager.NmeaParser(nmea);
		
	}

	@Override
	public void updateSky(Sky sky) {
		draw.setPoints(sky.getPoints());
	}
	
	
	@Override
	protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(mySensorListener);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(mySensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
	    sensorManager.registerListener(mySensorListener, magnetometer, SensorManager.SENSOR_DELAY_UI);
	}
	
	
	
	
	
	
	private class MySensorListener implements SensorEventListener {

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent se) {
			
			
			
			if(se.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
				accelerometerValues = se.values;
			if(se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
				magnetometerValues = se.values;
			
			rotation = new float[3];
			float[] R = new float[9];
			float[] I = new float[9];
			if (accelerometerValues != null && magnetometerValues != null) {
				SensorManager.getRotationMatrix(R, I, accelerometerValues, magnetometerValues);
				SensorManager.getOrientation(R, rotation);

				manager.updateViewPosition(rotation[0], rotation[1], rotation[2]);
			}
			
		}
		
	
	
}
	
	
	
	private class MyLocationListener implements android.location.LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	
}
