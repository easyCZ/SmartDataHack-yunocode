package com.example.edw;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Activity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Activity1.getB()==1)
		{
		// code for layout 2
			setContentView(R.layout.layout_2);
		}
		else if(Activity1.getB()==2)
		{
		// code for layout 3
			setContentView(R.layout.layout_3);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity2, menu);
		return true;
	}

}
