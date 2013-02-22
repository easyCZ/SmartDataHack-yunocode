// source: http://www.ezzylearning.com/tutorial.aspx?tid=1763429

package com.example.edw;

public class Weather {
	public static int ID;
	public int  icon;
	public String title, subtitle, distance;
	public Weather () {
		super();
	}
	
	public Weather(int ID, int icon, String title, String subtitle, String distance)
	{
		super();
		this.ID = ID;
		this.icon = icon;
		this.title = title;
		this.subtitle = subtitle;
		this.distance = distance;
	}
	
	public static int getID()
	{
		return ID;
	}

	@Override
	public String toString() {
		return "Weather [icon=" + icon + ", title=" + title + ", subtitle="
				+ subtitle + ", distance=" + distance + "]";
	}

}
