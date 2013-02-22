package com.example.edw;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResults extends Activity {

	private ListView listView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_search_results);
			
			// new Weather(id, icon, Title, subTitle, distance)
			 XMLParser parser = new XMLParser(null);
			 
			 Location loc = SearchPage.getCurrentLocation();
			 String[] args = SearchPage.getSearchData();
			 Results result = parser.doSearch(args, loc);
			 
			 
			 Weather weather_data[] = result.getWeatherArray(); 
//			 Weather weather_data[] = new Weather[]
//		                {
//		                    new Weather(1, R.drawable.ic_launcher, "Cloudy", "Monday", "1.11"),
//		                    new Weather(2, R.drawable.ic_launcher, "Showers", "Tuesday", "2.22"),
//		                    new Weather(3, R.drawable.ic_launcher, "Snow", "Wednesday", "3.33"),
//		                    new Weather(4, R.drawable.ic_launcher, "Storm", "Thursday", "4.44"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(5, R.drawable.ic_launcher, "Sunny", "Friday", "5.55"),
//		                    new Weather(6, R.drawable.ic_launcher, "testtest", "test", "6.66")
//		                };
		                
		                WeatherAdapter adapter = new WeatherAdapter(this, 
		                        R.layout.listview_item_row, weather_data);
		                
		                
		                listView1 = (ListView)findViewById(R.id.listView1);
		                 
		                View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
		                listView1.addHeaderView(header);
		                
		                listView1.setAdapter(adapter);
		                
		                listView1.setOnItemClickListener (new AdapterView.OnItemClickListener()
		                {
		                    @Override
		                	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		                	{
		                    	// add code here
		                        		                        
		                        Toast.makeText(getBaseContext(),"" + id , 500).show();
		                        
		                    }
		                });
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity2, menu);
		return true;
	}

}
