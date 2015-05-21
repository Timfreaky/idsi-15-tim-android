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

/**
 * @author Timothy McGarry & Florine Monnier
 * @version 1.0
 */
public class PathParser {

	public PathParser() {
		super();
	}
	
	/**
	 * Méthode qui parse le fichier docInf.kml pour récupérer les coordonnées qui forment un chemin. 
	 * Les coordonnées sont stockées dans une arraylist d'une arraylist de LatLng (allTracks).
	 * Jsoup est une librairie importée pour le parser
	 * 
	 * @param stream
	 * @return allTracks
	 */
	public ArrayList<ArrayList<LatLng>> getCoordinateArrays(InputStream stream) {
		ArrayList<ArrayList<LatLng>> allTracks = new ArrayList<ArrayList<LatLng>>();

		try {
			StringBuilder buf = new StringBuilder();

			InputStream json = stream;
			BufferedReader in = new BufferedReader(new InputStreamReader(json));
			String str;
			while ((str = in.readLine()) != null) {
				buf.append(str);
			}

			in.close();
			String html = buf.toString();
			//Jsoup comme parser
			Document doc = Jsoup.parse(html, "", Parser.xmlParser());
			//ArrayList de string (trackString) qui stocke les "coordinates" du document doc.kml
			ArrayList<String> tracksString = new ArrayList<String>();
			for (Element e : doc.select("coordinates")) {
				tracksString.add(e.toString().replace("<coordinates>", "")
						.replace("</coordinates>", ""));
			}

			for (int i = 0; i < tracksString.size(); i++) {
				ArrayList<LatLng> oneTrack = new ArrayList<LatLng>();
				ArrayList<String> oneTrackString = new ArrayList<String>(
						//On split la coordonnées en latitude et longitude
						Arrays.asList(tracksString.get(i).split("\\s+")));
				for (int k = 1; k < oneTrackString.size(); k++) {
					LatLng latLng = new LatLng(
							Double.parseDouble(oneTrackString.get(k).split(",")[0]),
							Double.parseDouble(oneTrackString.get(k).split(",")[1]));
					oneTrack.add(latLng);
				}
				allTracks.add(oneTrack);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allTracks;

	}

}
