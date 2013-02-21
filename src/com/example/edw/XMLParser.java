package com.example.edw;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
import android.os.AsyncTask;
import android.os.StrictMode;
import android.text.format.Time;


public class XMLParser {
	
	Resources resource;
	
	public XMLParser(Resources resource) {
		super();
		this.resource = resource;
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
	
	public Results doSearch (String[] args, Location location) {
		
		Results results = new Results();
		
		for(int i = 0; i < args.length; i++) {
			if (args[i].equals("Parks")) {
				results.addAll(Arrays.asList( getParks() ));
			} else if(args[i].equals("Sports")) {
				results.addAll(Arrays.asList( getSports() ));
			} else if(args[i].equals("ComCentres")) {
				results.addAll(Arrays.asList( getComCentres() ));
			} else if(args[i].equals("Allotments")) {
				results.addAll(Arrays.asList( getAllotments() ));
			} else if(args[i].equals("ConsAreas")) {
				results.addAll(Arrays.asList( getConsAreas() ));
			} else if(args[i].equals("YouthCentres")) {
				results.addAll(Arrays.asList( getYouthCentres() ));
			} else if(args[i].equals("Libraries")) {
				results.addAll(Arrays.asList( getLibraries() ));
			} else if(args[i].equals("MobLibs")) {
				results.addAll(Arrays.asList( getMobLibs() ));
			} else if(args[i].equals("PlayAreas")) {
				results.addAll(Arrays.asList( getPlayAreas() ));
			} else if(args[i].equals("Toilets")) {
				results.addAll(Arrays.asList( getToilets() ));
			} else if(args[i].equals("DayClubs")) {
				results.addAll(Arrays.asList( getDayClubs() ));
			} else if(args[i].equals("Trees")) {
				results.addAll(Arrays.asList( getTrees() ));
			} else if(args[i].equals("MuseumsGalls")) {
				results.addAll(Arrays.asList( getMuseumsGalls() ));
			} 
		}
		
		results.sort(location);
		
		return results;
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
				case 17: file = mgr.open("parks.xml");
				break;
				case 25: file = mgr.open("sports.xml");
				break;
				default: System.err.println("id does not match any local xml files");
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return url;
*/
		
	}
	
//	static Document docc;
	
	private static Document makeDocument(URL url) throws InterruptedException, ExecutionException, TimeoutException {
		
//		docc = null;
//		
//		class RetreiveFeedTask extends AsyncTask<URL, Document, Document> {
//
//		    private Exception exception;
//
//		    protected Document doInBackground(URL... urls) {
//		        try {
//		            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//					Document document = dBuilder.parse(urls[0].openStream());
//					return document;
//		        } catch (Exception e) {
//		            this.exception = e;
//		            return null;
//		        }
//		    }
//
//		    protected void onPostExecute(Document document) {
//		        // TODO: check this.exception 
//		        // TODO: do something with the feed
//		    	System.out.println("Have result");
//		    	docc = document;
//		    }
//		 }
//		
//		
//		AsyncTask<URL, Document, Document> task = new RetreiveFeedTask().execute(url);
//		task.get(1000, TimeUnit.HOURS);
//		System.out.println("HERE");
//		
//		return docc;
//		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		
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
				
				String name = null;
				
				for (int i = 0; i < entryFields.getLength(); i++) {
					Element field = (Element)entryFields.item(i);
					
					
					Double latitude = Double.NaN;
					Double longitude = Double.NaN;
					
					if (field.getAttribute("name").equals("Name")) {
//						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}
					
					if (field.getAttribute("type").equals("map") && !field.getTextContent().equals("") && field.getTextContent().contains(".")) {
						String coord[] = field.getTextContent().split(",");
//						System.out.println(coord[0] + " " + coord[1]);
						latitude = Double.parseDouble(coord[0]);
						longitude = Double.parseDouble(coord[1]);
//						System.out.println("coordinate is : " + field.getTextContent());
						
						places[j] = new DataPlace(name, latitude, longitude);
					}	
				}
			}
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("Places with id " +id + " found: "+places.length);
		return places;
	}	

}
