package com.example.edw;

import java.io.File;
import java.util.Iterator;

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
//		test.getParks();
//		test.getSports();

		Results result = test.getAll();
		
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
	
