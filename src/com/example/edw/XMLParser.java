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

	public DataObject[] getParks(){  // Background, Facilities
		return getPlaces(17, "Park.");
	}

	public DataObject[] getSports(){ // "Facilities include":Facilities   Opening hours  Activities
		return getPlaces(25, "");
	}

	public DataObject[] getComCentres(){ // Key Activities, "Facilities include": Facilities, 
		return getPlaces(24, "Community Centre.");
	}

	public DataObject[] getAllotments(){  //Facilities, Waiting time
		return getPlaces(32, "Allotment.");
	}

	/*public DataObject[] getConsAreas(){    // no location data
		return getPlaces(31);
	}*/

	public DataObject[] getYouthCentres(){ // Activities
		return getPlaces(35, "Youth Centre.");
	}

	public DataObject[] getLibraries(){// Details, Facilities, Book groups, Bookbug Sessions, Other events, Opening hours, 
		return getPlaces(12, "Library.");
	}

	public DataObject[] getMobLibs(){  //Day and time
		return getPlaces(16, "Mobile Library.");
	}

	public DataObject[] getPlayAreas(){ // Site instead of name, Play facilities
		return getPlaces(60, "Play Area.");
	}

	public DataObject[] getToilets(){   // aka "public conveniences", Facilities, Opening times, Toilet instead of name
		return getPlaces(61, "Toilet.");
	}

	public DataObject[] getDayClubs(){ // Days and times, Services provided
		return getPlaces(105, "");
	}

	public DataObject[] getTrees(){  // Name of tree collection instead of Name, should include tree?? , Species ?, Information
		return getPlaces(107, "Tree.");
	}

	public DataObject[] getMuseumsGalls(){ //Details, Opening Hours, 
		return getPlaces(11, "");
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

				String name = "";
				Double latitude = Double.NaN;
				Double longitude = Double.NaN;
				String info = type;
				String link = "http://www.edinburgh.gov.uk/directory_record/"+ entryId +"/";


				// for each data field:
				for (int i = 0; i < entryFields.getLength(); i++) {
					Element field = (Element)entryFields.item(i);

					// name

					if (field.getAttribute("name").equals("Name") ||
						field.getAttribute("name").equals("Name of tree collection") ||
						field.getAttribute("name").equals("Toilet") ||
						field.getAttribute("name").equals("Site") &&
						!field.getTextContent().equals("")) {
						System.out.println("Name is: " + field.getTextContent());
						name = field.getTextContent();
					}

					// info
					if ((field.getAttribute("name").equals("Background") || 
						field.getAttribute("name").equals("Details") ||
						field.getAttribute("name").equals("Species") || 
						field.getAttribute("name").equals("Information")) && 
						!field.getTextContent().equals("")) {   
						System.out.println("Description is: " + field.getTextContent());
						info = info + " "+field.getTextContent()+ ".";
					}
					if ((field.getAttribute("name").equals("Activities") || 
						field.getAttribute("name").equals("Key Activities") ||
						field.getAttribute("name").equals("Book groups") ||
						field.getAttribute("name").equals("Bookbug Sessions") ||
						field.getAttribute("name").equals("Other events")) && 
						!field.getTextContent().equals("")) {
						System.out.println("Activities are: " + field.getTextContent());
						info = info + " Activities: " + field.getTextContent() +".";
					}

					if (field.getAttribute("name").equals("Services provided") && 
						!field.getTextContent().equals("")) {
						info = field.getTextContent() + ". "+ info;
					}

					if ((field.getAttribute("name").equals("Facilities") ||
						field.getAttribute("name").equals("Play facilities")) && 
						!field.getTextContent().equals("")) {
						System.out.println("Facilities are: " + field.getTextContent());
						info = info + " Facilities: " + field.getTextContent() +".";
					}

					if (field.getAttribute("name").equals("Vacant") && 
						!field.getTextContent().equals("")) {   // allotments
						System.out.println("Vacant plots are: " + field.getTextContent());
						info = info + " Vacant plots: "+ field.getTextContent() +".";
					}

					if (field.getAttribute("name").equals("Waiting time") && 
						!field.getTextContent().equals("")) {   // allotments
						System.out.println("Waiting time is: " + field.getTextContent());
						info = info + " Waiting time: "+ field.getTextContent() +".";
					}

					if ((field.getAttribute("name").equals("Opening Hours") || 
						field.getAttribute("name").equals("Opening hours") ||
						field.getAttribute("name").equals("Opening times") ||
						field.getAttribute("name").equals("Day and time") ||
						field.getAttribute("name").equals("Days and times")) && 
						!field.getTextContent().equals("")){
						System.out.println("Opening times is " + field.getTextContent());
						info = info + " Open on: " + field.getTextContent() +".";
						}


					// location
					if (field.getAttribute("type").equals("map") && (!field.getTextContent().equals("")) && field.getTextContent().contains(".")) {
						String coord[] = field.getTextContent().split(",");
//						System.out.println(coord[0] + " " + coord[1]);
						latitude = Double.parseDouble(coord[0]);
						longitude = Double.parseDouble(coord[1]);
//						System.out.println("coordinate is : " + field.getTextContent());

						places[j] = new DataPlace(name, latitude, longitude, info, link);
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