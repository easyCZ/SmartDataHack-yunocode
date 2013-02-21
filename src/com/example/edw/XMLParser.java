package com.example.edw;

import java.io.File;
import org.apache.commons.io.FileUtils.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLParser {
	
	public static void main(String[] args) {
		System.out.println("ok");
		XMLParser test = new XMLParser();
		
		System.out.println("Parks:");
		test.getParks();
		
		System.out.println("Sports:");
		test.getSports();
		
		test.getComCentres();
		test.getConsAreas();
		test.getDayClubs();
		test.getLibraries();
		test.getMobLibs();
		test.getMuseumsGalls();
		test.getAllotments();
		test.getPlayAreas();
		test.getToilets();
		test.getTrees();
		test.getYouthCentres();
	}
	
	public DataObject[] getParks(){
		return getPlaces(17);
	}
	
	public DataObject[] getSports(){
		return getPlaces(25);
	}
	
	public DataObject[] getComCentres(){
		return getPlaces(24);
	}
	
	public DataObject[] getAllotments(){
		return getPlaces(32);
	}
	
	public DataObject[] getConsAreas(){
		return getPlaces(31);
	}
	
	public DataObject[] getYouthCentres(){
		return getPlaces(35);
	}
	
	public DataObject[] getLibraries(){
		return getPlaces(12);
	}
	
	public DataObject[] getMobLibs(){
		return getPlaces(16);
	}
	
	public DataObject[] getPlayAreas(){
		return getPlaces(60);
	}
	
	public DataObject[] getToilets(){   // aka "public conveniences"
		return getPlaces(61);
	}
	
	public DataObject[] getDayClubs(){
		return getPlaces(105);
	}
	
	public DataObject[] getTrees(){
		return getPlaces(107);
	}
	
	public DataObject[] getMuseumsGalls(){
		return getPlaces(11);
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

/*		for testing, using local xml files:
		switch (id) {
		case 17: url = new URL("src/data-xml/parks.xml");
		break;
		case 25: url = new URL("src/data-xml/sports.xml");
		break;
		default: System.err.println("id does not match any local xml files");
		}			
			
		return url;
*/
		
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
	
	private DataObject[] getPlaces(int id) {
		DataObject[] places = null;
		
		try {
			URL url = createCouncilURL(id);
			
			Document doc = makeDocument(url);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			
			Element docElement = doc.getDocumentElement();
			NodeList entries = docElement.getElementsByTagName("entry");
			
			places = new DataObject[entries.getLength()];
			
			for (int j = 0; j < entries.getLength(); j++) {
				Element entry = (Element)entries.item(j);
				
				NodeList entryFields = entry.getElementsByTagName("field");
				
				for (int i = 0; i < entryFields.getLength(); i++) {
					Element field = (Element)entryFields.item(i);
					
					String name = null;
					Double latitude = Double.NaN;
					Double longitude = Double.NaN;
					
					if (field.getAttribute("name").equals("Name")) {
						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}
					
					if (field.getAttribute("type").equals("map") && !field.getTextContent().equals("") && field.getTextContent().contains(".")) {
						String coord[] = field.getTextContent().split(",");
						System.out.println(coord[0] + " " + coord[1]);
						latitude = Double.parseDouble(coord[0]);
						longitude = Double.parseDouble(coord[1]);
						System.out.println("coordinate is : " + field.getTextContent());
						
						places[j] = new DataPlace(name, latitude, longitude);
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
