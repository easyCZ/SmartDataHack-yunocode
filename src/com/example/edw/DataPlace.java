package com.example.edw;


public class DataPlace extends DataObject {
	
	private double lat,lng;
	private String name;
	private String info;
	private String link;
	
	public DataPlace(String name, double lat, double lng, String info, String link) {
		setName(name);
		setLongtitude(lng);
		setLatitude(lat);
		setInfo(info);
		setLink(link);
		distance = Double.NaN;
	}

	

	@Override
	public String toString() {
		return "DataPlace [lat=" + lat + ", lng=" + lng + ", name=" + name
				+ ", distance: " + distance + "]";
	}
	
	
	
	@Override
	void setName(String name) { this.name = name;	}

	@Override
	String getName() { return name; }

	@Override
	void setLongtitude(double lng) { this.lng = lng; }

	@Override
	double getLongtitude() { return lng; }

	@Override
	void setLatitude(double lat) { this.lat = lat;}

	@Override
	double getLatitude() { return lat;}
	
	@Override
	void setInfo(String info) { this.info = info;	}

	@Override
	String getInfo() { return info; }
	
	@Override
	void setLink(String link) { this.link = link;	}

	@Override
	String getLink() { return link; }
	
	
	
	@Override
	double getDistance() {
		// TODO Auto-generated method stub
		return distance;
	}
	
}
