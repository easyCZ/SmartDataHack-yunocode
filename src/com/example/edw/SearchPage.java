package com.example.edw;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class SearchPage extends Activity implements LocationListener, OnTouchListener{
	
	public static Location currentLocation = null;
    public boolean updateGPSlocation = true;
	private LocationManager locationManager;
	private static final long MIN_TIME = 1 * 60 * 500; //.5 minute
	private EditText searchText;
	private Button b;
	private CheckBox check_box;
    private Button searchButton;
    private ScrollView scrollview;
    private static String[] searchData;
    
    public static Location getCurrentLocation() {
    	return currentLocation;
    }
    
    public static String[] getSearchData() {
    	return searchData;
    }

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        
        b = (Button) findViewById(R.id.searchButton);
        check_box = (CheckBox) findViewById(R.id.select_all_checkbox);
        
        final CheckBox[] idList = new CheckBox[] {
        		(CheckBox) findViewById(R.id.CheckBox10),
        		(CheckBox) findViewById(R.id.CheckBox09),
        		(CheckBox) findViewById(R.id.CheckBox08),
        		(CheckBox) findViewById(R.id.CheckBox07),
        		(CheckBox) findViewById(R.id.CheckBox06),
        		(CheckBox) findViewById(R.id.CheckBox05),
        		(CheckBox) findViewById(R.id.CheckBox04),
        		(CheckBox) findViewById(R.id.CheckBox03),
        		(CheckBox) findViewById(R.id.CheckBox02),
        		(CheckBox) findViewById(R.id.CheckBox01),
        		(CheckBox) findViewById(R.id.CheckBox9),
        		(CheckBox) findViewById(R.id.CheckBox8)
        };
        
        
        View.OnClickListener handler = new View.OnClickListener() {
        		@Override
     			public void onClick(View v) {
     				if(v == check_box)
     				{
     					if(check_box.isChecked())
     			        {
	     					for(CheckBox tmp : idList)
	     		        		tmp.setChecked(true);
     			        }
     					else
     					{
     						for(CheckBox tmp : idList)
	     		        		tmp.setChecked(false);
     					}
     				}
     			}
        };
        check_box.setOnClickListener(handler);
        	
        
//        View.OnClickListener handler1 = new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(v == b)
//				{
//					Intent intent1 = new Intent(getApplicationContext(), SearchResults.class);
//					startActivity(intent1);
////					getGeoLocation(searchText.getText().toString());
//				}
//			}
//        };
//        b.setOnClickListener(handler1);
       searchButton = (Button) findViewById(R.id.searchButton);
       searchButton.setOnClickListener(new OnClickListener() {
           
           public void onClick(View v) {
                   Context context = getApplicationContext();
                   CharSequence text;
                   if (currentLocation == null) text = "null";
                   else text = "has location";
			           	int duration = Toast.LENGTH_SHORT;
			           	Toast toast = Toast.makeText(context, text, duration);
			           	toast.show();
                  
                   if (searchButton.getText().equals("Find location")) {
                           getGeoLocation(searchText.getText().toString());
                   } else if (searchButton.getText().equals("Be Awesome")) {
                           // Move to next view
                         searchData = getSearchOptions(idList, names);
                         Intent intent1 = new Intent(getApplicationContext(), SearchResults.class);
                         startActivity(intent1);
                          
                   }
           }
       });
       
       locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);
       scrollview = (ScrollView) findViewById(R.id.scrollView1);
       scrollview.setOnTouchListener(this);
        // Set text field editable based on GPS availability
        searchText = (EditText) findViewById(R.id.editText1);
        
        searchText.setOnTouchListener(this);
        searchText.setSelected(false);
        
        determineLocation();
    }
    
    private void determineLocation() {
    	if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	searchText.setText("Current location Available");
//        	searchText.setClickable(false);
//        	searchText.setFocusable(false);
        	
        } 
    	else {
        	searchText.setText("Please enter location.");
        	searchText.setFocusable(true);
//        	searchText.setSelected(false);
        }
    }
    
    
 // REMEMBER TO CHECK THE INPUT
    public Location getGeoLocation(String address) {
        Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
                List<Address> address2 = geoCoder.getFromLocationName(address, 1);
                if (address2.size() > 0) {
                        Location loc = new Location("user_input_location");
                        loc.setLatitude(address2.get(0).getLatitude());
                        loc.setLongitude(address2.get(0).getLongitude());
                        currentLocation = loc;
                        updateGPSlocation = true;
                       
                        Context context = getApplicationContext();
                        CharSequence text = "Location found.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        
                        searchText.setVisibility(View.INVISIBLE);
                        searchButton.setText("Be Awesome");
                       
                        return loc;
                }
        } catch (IOException e) {
               
        }
        return null;
    }
    
    

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_page, menu);
        return true;
    }


    // If user hasn't entered location, update the location from GPS
    public void onLocationChanged(Location location) {
            if (updateGPSlocation) {
                    currentLocation = location;
            }
    }

    public void onProviderDisabled(String provider) {}
    public void onProviderEnabled(String provider) {}
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    
    public boolean onTouch(View v, MotionEvent event) {
        Button butt = (Button) findViewById(R.id.searchButton);
        if (v == scrollview) {
                butt.setText("Be Awesome");
        } else if (v == searchText) {
                searchText.setText("");
                butt.setText("Find location");
        }
        return false;
}
	
	private String[] names = {
			"Parks and Gardens",
			"Sports",
			"Community Centers",
			"Allotments",
			"Youth Centers",
			"Libraries",
			"Mobile Librariews",
			"Play Areas",
			"Toilets",
			"Day Clubs",
			"Trees",
			"Museums and Galleries"
	};
	
    private String[] getSearchOptions(CheckBox[] checkboxes, String[] names) {
        List<String> tickedOptions = new LinkedList<String>();
       
        if (checkboxes.length == names.length) {
               
                for (int i = 0; i < checkboxes.length; i++) {
                        if (checkboxes[i].isChecked()) tickedOptions.add(names[i]);
                }
        }
        return tickedOptions.toArray(new String[0]);
    }

    
}
