package com.example.edw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

	Button b_layout1;
	CheckBox check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b_layout1 = (Button) findViewById(R.id.button1);
		check = (CheckBox) findViewById (R.id.checkBox1);
		
		View.OnClickListener handler = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v == b_layout1)
				{
					Intent intent1 = new Intent(getApplicationContext(), Activity1.class);
					MainActivity.this.startActivity(intent1);
					if(check.isChecked())
						finish();
				}
				
			}
		};
		
		b_layout1.setOnClickListener(handler);
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
