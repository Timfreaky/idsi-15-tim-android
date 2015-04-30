package ch.unige.idsi.sportit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;


import com.google.android.gms.maps.model.LatLng;

public class InfrParser {
	
	public InfrParser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<LatLng> getCoordinateArrays(InputStream stream) {
	    //ArrayList<LatLng> allPlaces = new ArrayList<LatLng>();
	    ArrayList<LatLng> onePlace = new ArrayList<LatLng>();

	    try {
	        StringBuilder buff = new StringBuilder();
	        
	        InputStream json = stream;
	        BufferedReader in = new BufferedReader(new InputStreamReader(json));
	        String string;
	                      String buffer;
	        while ((string = in.readLine()) != null) {
	            buff.append(string);
	        }

	        in.close();
	        String html = buff.toString();
	        Document doc = Jsoup.parse(html, "", Parser.xmlParser());
	        ArrayList<String> tracksString = new ArrayList<String>();

	        for (Element e : doc.select("coordinates")) {
	            tracksString.add(e.toString().replace("<coordinates>", "").replace("</coordinates>", ""));
	        }

	        for (int i = 0; i < tracksString.size(); i++) {
	          
	            ArrayList<String> onePlaceString = new ArrayList<String>(Arrays.asList(tracksString.get(i).split("\\s+")));
	            for (int k = 1; k < onePlaceString.size(); k++) {
	                LatLng latLng = new LatLng(Double.parseDouble(onePlaceString.get(k).split(",")[0]),
	                        Double.parseDouble(onePlaceString.get(k).split(",")[1]));
	                onePlace.add(latLng);
	            }
	            
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println(onePlace);
	    return onePlace;
	    
	}
	public ArrayList<String> getNamesArrays(InputStream stream) {
		 ArrayList<String> namesString = new ArrayList<String>();
		 try {
		        StringBuilder buffe = new StringBuilder();
		        
		        InputStream json = stream;
		        BufferedReader in = new BufferedReader(new InputStreamReader(json));
		        String string;
		        String buffer;
		        while ((string = in.readLine()) != null) {
		            buffe.append(string);
		        }

		        in.close();
		        String html = buffe.toString();
		        Document doc = Jsoup.parse(html, "", Parser.xmlParser());
		       
		        for (Element e : doc.select("SimpleData")) {
		        	if(e.attr("name").equalsIgnoreCase("TYPE")){
		        		System.out.println(e.toString());
		            namesString.add(e.toString().replace("<simpledata name=\"TYPE\">", "").replace("</simpledata>", ""));
		        	}
		        }
		 } catch (Exception f) 
		 	{
		        f.printStackTrace();
		    }
		
		return namesString;
		 
	}
}

