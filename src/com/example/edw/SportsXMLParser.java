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


public class SportsXMLParser {
	
	private File file;
	private URI uri;
	
	
	private File getXMLSource(URI uri) {
		try {
			uri = new URI("http://www.edinburgh.gov.uk/api/directories/25/entries.xml?api_key=1b8460046f414457cc69bc46cfb5d6ce&per_page=100&page=1");
		} catch (URISyntaxException e) {
			System.err.println("CANNOT PARSE URI");
		}
		
		file = new File(uri);
		
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
	
	public static void main(String[] args) {
		try {
//			URI uri = new URI("http://www.edinburgh.gov.uk/api/directories/17/entries.xml?api_key=1b8460046f414457cc69bc46cfb5d6ce&per_page=100&page=1");
			File fXmlFile = new File("src/data-xml/sports.xml");
			
			Document doc = makeDocument(fXmlFile);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			
			
			
			
			Element docElement = doc.getDocumentElement();
			
			NodeList entries = docElement.getElementsByTagName("entry");
			
			DataObject[] sports = new DataObject[entries.getLength()];
			
			for (int j = 0; j < entries.getLength(); j++) {
				Element entry = (Element)entries.item(j);
				
				NodeList entryFields = entry.getElementsByTagName("field");
				
				for (int i = 0; i < entryFields.getLength(); i++) {
					Element field = (Element)entryFields.item(i);
					
					String name = null;
					Double latitude = Double.NaN;
					Double longitude = Double.NaN;
					
					if (field.getAttribute("id").equals("456")) {
//						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}
					
					if (field.getAttribute("id").equals("467")) {
						String coord[] = field.getTextContent().split(",");
//						System.out.println(coord[0] + " " + coord[1]);
						latitude = Double.parseDouble(coord[0]);
						longitude = Double.parseDouble(coord[1]);
//						System.out.println("coordinate is : " + field.getTextContent());
					}
					
					sports[j] = new DataPlace(name, latitude, longitude);
				}
			}
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		  }
		 
		
		

}
