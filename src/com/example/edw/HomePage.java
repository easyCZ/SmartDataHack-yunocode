package com.example.edw;

import android.R.color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class HomePage extends Activity implements LocationListener{
	
	private LocationManager locationManager;
	private static final long MIN_TIME = 1 * 60 * 1000; //1 minute
	private TextView gpsStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		Intent intent = new Intent(this, MainActivity.class);
		
		
		gpsStatus = (TextView) findViewById(R.id.textView1);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.activity_home_page, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location loc) {
	    gpsStatus.setText("GPS active");
		
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
