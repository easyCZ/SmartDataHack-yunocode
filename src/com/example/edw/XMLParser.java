package com.example.edw;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.location.Location;


public class XMLParser {
	
	Resources resource;
	
	public XMLParser(Resources resource) {
		super();
		this.resource = resource;
	}
	
	public DataObject[] getParks(){  // Background, Facilities
		return getPlaces(17, "Park");
	}
	
	public DataObject[] getSports(){ // "Facilities include":Facilities   Opening hours  Activities
		return getPlaces(25, "");
	}
	
	public DataObject[] getComCentres(){ // Key Activities, "Facilities include": Facilities, 
		return getPlaces(24, "Community Centre");
	}
		
	public DataObject[] getAllotments(){  //Facilities, Waiting time
		return getPlaces(32, "Allotment");
	}
		
	/*public DataObject[] getConsAreas(){    // no location data
		return getPlaces(31);
	}*/
		
	public DataObject[] getYouthCentres(){ // Activities
		return getPlaces(35, "Youth Centre");
	}
		
	public DataObject[] getLibraries(){// Details, Facilities, Book groups, Bookbug Sessions, Other events, Opening hours, 
		return getPlaces(12, "Library");
	}
	
	public DataObject[] getMobLibs(){  //Day and time
		return getPlaces(16, "Mobile Library");
	}

	public DataObject[] getPlayAreas(){ // Site instead of name, Play facilities
		return getPlaces(60, "Play Area");
	}
		
	public DataObject[] getToilets(){   // aka "public conveniences", Facilities, Opening times, Toilet instead of name, include this in info!
		return getPlaces(61, "Toilet");
	}
		
	public DataObject[] getDayClubs(){ // Days and times, Services provided
		return getPlaces(105, null);
	}
		
	public DataObject[] getTrees(){  // Name of tree collection instead of Name, should include tree?? , Species ?, Information
		return getPlaces(107, "Tree");
	}
		
	public DataObject[] getMuseumsGalls(){ //Details, Opening Hours, 
		return getPlaces(11, null);
	}
		
	private URL createCouncilURL(int id) {
		URL url = null;
		try {
			url = new URL("http://www.edinburgh.gov.uk/api/directories/"+id+"/entries.xml?api_key=1b8460046f414457cc69bc46cfb5d6ce&per_page=100&page=1");
		} catch (MalformedURLException e) {
			System.err.println("CANNOT PARSE URI");
			e.printStackTrace();
		}	
		return url;
		
		
	}
	
	private static Document makeDocument(URL url) {
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(url.openStream());
//			document.getDocumentElement().normalize();
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private DataObject[] getPlaces(int id, String type) {
		DataObject[] places = null;
		
		try {
			URL url = createCouncilURL(id);
			Document doc = makeDocument(url);
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			
			Element docElement = doc.getDocumentElement();
			NodeList entries = docElement.getElementsByTagName("entry");
			
			places = new DataObject[entries.getLength()];
			
			// for each place:
			for (int j = 0; j < entries.getLength(); j++) {
				
				Element entry = (Element)entries.item(j);
				String entryId = entry.getAttribute("id");
				NodeList entryFields = entry.getElementsByTagName("field");
				
				String name = null;
				Double latitude = Double.NaN;
				Double longitude = Double.NaN;
				String info = type;
				String link = "http://www.edinburgh.gov.uk/directory_record/"+ entryId +"/";
				
				
				// for each data field:
				for (int i = 0; i < entryFields.getLength(); i++) {
					Element field = (Element)entryFields.item(i);
					
					// name
					if (field.getAttribute("name").equals("Name")) {
						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}
					if (field.getAttribute("name").equals("Site") && name.equals(null)) {
						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}
					if (field.getAttribute("name").equals("Toilet") && name.equals(null)) {
						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}
					
					if (field.getAttribute("name").equals("Name of tree collection") && name.equals(null)) {
						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}
					
					// info
					if (field.getAttribute("name").equals("Background")) {   // parks
						System.out.println("Info is: " + field.getTextContent());
						info = field.getTextContent();
					}
					if (field.getAttribute("name").equals("Facilities") && info.equals(null)) {
						System.out.println("Info is: " + field.getTextContent());
						info = info + ". Facilities include: " + field.getTextContent();
					}
					
					if (field.getAttribute("name").equals("Vacant") && info.equals(null)) {   // allotments
						System.out.println("Info is: " + field.getTextContent());
						info = info + ". Vacant plots: "+ field.getTextContent();
					}
					
					if (field.getAttribute("name").equals("Background")) {   // parks
						System.out.println("Info is: " + field.getTextContent());
						info = field.getTextContent();
					}
					
					
					// location
					if (field.getAttribute("type").equals("map") && !field.getTextContent().equals("") && field.getTextContent().contains(".")) {
						String coord[] = field.getTextContent().split(",");
						System.out.println(coord[0] + " " + coord[1]);
						latitude = Double.parseDouble(coord[0]);
						longitude = Double.parseDouble(coord[1]);
						System.out.println("coordinate is : " + field.getTextContent());
						
						places[j] = new DataPlace(name, latitude, longitude, info, link);
					}	
				}
			}
			
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Places with id " +id + " found: "+places.length);
		return places;
	}	

}
