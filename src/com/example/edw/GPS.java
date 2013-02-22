package com.example.edw;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GPS extends Activity  implements OnClickListener, LocationListener{
	
	private LocationManager locationManager;
	private static final long MIN_TIME = 1 * 60 * 1000; //1 minute
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);
//		findViewById(R.id.settings_button).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		Intent inte = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
//		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(inte);
		
	}

	@Override
	public void onLocationChanged(Location loc) {
	       double lat = loc.getLatitude();
	       double lng = loc.getLongitude();
//	       ((TextView)findViewById(R.id.locat_label)).setText
//	         ("Fine Location = (lat:"+lat+",lng:"+lng+")");
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);
	}
	
	protected void onPause() {
	      super.onPause();
	      locationManager.removeUpdates(this);
	}

}
