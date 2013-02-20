package com.example.edw;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLParser {
	
	private URI parksuri;
	private URI sportsuri;
	
	public static void main(String[] args) {
		System.out.println("ok");
		XMLParser test = new XMLParser();
		
		System.out.println("Parks:");
		test.getParks();
		
		System.out.println("Sports:");
		test.getSports();
	}
	
	public DataObject[] getParks(){
		return getPlaces(17);
	}
	
	public DataObject[] getSports(){
		return getPlaces(25);
	}
	
	private File getXMLSource(int id) {

		URI uri = null;
		File file = null;
//		try {
//			uri = new URI("http://www.edinburgh.gov.uk/api/directories/"+id+"/entries.xml?api_key=1b8460046f414457cc69bc46cfb5d6ce&per_page=100&page=1");
//		} catch (URISyntaxException e) {
//			System.err.println("CANNOT PARSE URI");
//		}
//      file = new File(uri);
		
//		for testing, using local xml files:
		switch (id) {
		case 17: file = new File("src/data-xml/parks.xml");
		break;
		case 25: file = new File("src/data-xml/sports.xml");
		break;
		default: System.err.println("id does not match any local xml files");
		}			
			
		return file;
	}
	
	private static Document makeDocument(File file) {
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
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
			File fXmlFile = getXMLSource(id);
			
			Document doc = makeDocument(fXmlFile);
			
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
					
					if (field.getAttribute("type").equals("map") && !field.getTextContent().equals("")) {
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
		
		return places;
	}	

}
