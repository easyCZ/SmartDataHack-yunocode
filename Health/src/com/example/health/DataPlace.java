
public class DataPlace extends DataObject {
	
	private double lat,lng;
	private String name;
	
	public DataPlace(String name, double lat, double lng) {
		setName(name);
		setLongtitude(lng);
		setLatitude(lat);
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

}
