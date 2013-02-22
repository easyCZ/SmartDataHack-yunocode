package com.example.edw;


public abstract class DataObject {

	protected double distance;

	abstract void setName(String name);
	abstract String getName();
	abstract void setLongtitude(double lng);
	abstract double getLongtitude();
	abstract void setLatitude(double lat);
	abstract double getLatitude();
	abstract void setInfo(String info);
	abstract String getInfo();
	abstract void setLink(String link);
	abstract String getLink();
	abstract double getDistance();

	/*
	 * Returns distance in miles with 2 precision points
	 */
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		  double theta = lng1 - lng2;
		  double dist = Math.sin(lat1 * Math.PI / 180.0) * Math.sin(lat2 * Math.PI / 180.0) + Math.cos(lat1 * Math.PI / 180.0) * Math.cos(lat2 * Math.PI / 180.0) * Math.cos(theta * Math.PI / 180.0);
	      dist = Math.acos(dist);
	      dist = dist * 180 / Math.PI;
		  dist = dist * 60 * 1.1515;
		  dist = dist * 100;
		  dist = Math.round(dist);
		  dist = dist/100;
		  return dist;
    }

	public double distanceTo(double lat, double lon) {
		distance = distFrom(lat, lon, getLatitude(), getLongtitude());
		return distance;
	}
}