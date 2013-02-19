package com.example.health;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{

    Button b_layout1, b_layout2;
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_layout1 = (Button) findViewById(R.id.button1);
        b_layout2 = (Button) findViewById(R.id.button2);

        
        View.OnClickListener handler = new View.OnClickListener()
        {
			
			@Override
			public void onClick(View v)
			{
				if(v == b_layout1)
				{
					Intent intentMain = new Intent(MainActivity.this, Activity1.class);
					MainActivity.this.startActivity(intentMain);
				}
				
				if (v == b_layout2)
				{
					Intent intentMain = new Intent(MainActivity.this, Activity2.class);
					MainActivity.this.startActivity(intentMain);
				}
			}
        };
        
        b_layout1.setOnClickListener(handler);
        b_layout2.setOnClickListener(handler);
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
