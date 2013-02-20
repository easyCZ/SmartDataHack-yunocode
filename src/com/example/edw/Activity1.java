package com.example.edw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class Activity1 extends Activity {

	ImageButton b1_layout2, b2_layout2;
	static int b=0;
	public static int getB()
	{
		return b;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act1);
		
		b1_layout2 = (ImageButton) findViewById(R.id.btn1);
		b2_layout2 = (ImageButton) findViewById(R.id.btn2);
		
		View.OnClickListener handler = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v == b1_layout2)
				{
					
					b=1;
				}
				if(v == b2_layout2)
				{
					b=2;
				}
				Intent intent1 = new Intent(getApplicationContext(), Activity2.class);
				Activity1.this.startActivity(intent1);
				
			}
		};
		
		b1_layout2.setOnClickListener(handler);
		b2_layout2.setOnClickListener(handler);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity1, menu);
		return true;
	}

}
