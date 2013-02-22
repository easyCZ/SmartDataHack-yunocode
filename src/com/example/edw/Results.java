package com.example.edw;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import android.location.Location;

public class Results {
	
	LinkedList<DataObject> results = null;

	public Results() {
		this.results = new LinkedList<DataObject>();
	}
	
	public void addAll(Collection <? extends DataObject> collection) {
		this.results.addAll(collection);
	}
	
	/* 
	 * Sort results by proximity to given location
	 */
	public void sort(Location location) {
		
		final double latitude = location.getLatitude();
		final double longitude = location.getLongitude();
		
		Comparator<DataObject> comparator = new Comparator<DataObject>() {
										         @Override
										         public int compare(DataObject o1, DataObject o2) {
										             return compareDataObject(latitude, longitude, o1, o2);
										         }
										     };
		
		Collections.sort(results, comparator);
	}
	
	private int compareDataObject(double lat, double lon, DataObject o1, DataObject o2) {
		if (o1 == null || o2 == null) {
			return 1;
		} else
		{
			if (o1.distanceTo(lat, lon) > o2.distanceTo(lat, lon)) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	public Weather[] getWeatherArray() {
		LinkedList<Weather> weatherArray = new LinkedList<Weather>();
		
		Iterator<DataObject> iter = results.iterator();
		int i = 0;
        while(iter.hasNext() && i < 10)
        {
            Weather weather = dataToWeather(iter.next());
            weatherArray.add(weather);
            i++;
        }  
		
		return weatherArray.toArray(new Weather[0]);
	}
	
	private Weather dataToWeather(DataObject data) {
		Weather item = new Weather(0, R.drawable.ic_launcher, data.getName(), "", ""+data.getDistance());
		return item;
	}
	
	public LinkedList<DataObject> getResults() {
		return results;
	}
	
}
