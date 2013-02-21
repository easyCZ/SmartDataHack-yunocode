package com.example.edw;

import java.io.File;
import java.util.Iterator;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		XMLParser test = new XMLParser(getResources());

		// REPLACE THIS WITH ACTUAL SEARCH
//		Results result = new Results();
		
		String[] query = {"Sports", "Trees"};
		
		Location location = new Location("ANDRETEST");
		location.setLatitude(0);
		location.setLongitude(0);
		
		Results result = test.doSearch(query, location);
		
		Iterator<DataObject> iter = result.getResults().iterator();
        while(iter.hasNext())
        {
            System.out.println(iter.next());
        }  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showHomepage(View v) {
		finish();
	}
}
	
