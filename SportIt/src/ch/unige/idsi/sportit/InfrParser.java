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
public class InfrParser {

	public InfrParser() {
		super();

	}

	/**
	 * M�thode qui parse le fichier docInf.kml pour r�cup�rer les coordonn�es de chaque infrastructure. 
	 * Les coordonn�es sont stock�es dans une arraylist de LatLng (onePlace).
	 * Jsoup est une librairie import�e pour le parser
	 * 
	 * @param stream
	 * @return onePlace
	 */
	public ArrayList<LatLng> getCoordinateArrays(InputStream stream) {
		ArrayList<LatLng> onePlace = new ArrayList<LatLng>();

		try {
			StringBuilder buff = new StringBuilder();

			InputStream json = stream;
			BufferedReader in = new BufferedReader(new InputStreamReader(json));
			String string;
			while ((string = in.readLine()) != null) {
				buff.append(string);
			}

			in.close();
			String html = buff.toString();
			Document doc = Jsoup.parse(html, "", Parser.xmlParser()); //Jsoup comme parser
			ArrayList<String> tracksString = new ArrayList<String>(); //Arraylist de coordonn�es

			for (Element e : doc.select("coordinates")) {
				tracksString.add(e.toString().replace("<coordinates>", "")
						.replace("</coordinates>", ""));
			}

			for (int i = 0; i < tracksString.size(); i++) {

				ArrayList<String> onePlaceString = new ArrayList<String>(
						Arrays.asList(tracksString.get(i).split("\\s+")));
				for (int k = 1; k < onePlaceString.size(); k++) {
					LatLng latLng = new LatLng(
							Double.parseDouble(onePlaceString.get(k).split(",")[0]),
							Double.parseDouble(onePlaceString.get(k).split(",")[1]));
					onePlace.add(latLng);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return onePlace;

	}
	
	/**
	 * M�thode qui parse le fichier docInf.kml pour r�cup�rer les titres (ou type) de chaque infrastructure (utilis�s pour les markers sur la cartes).
	 * 
	 * @param stream
	 * @return namesString
	 */
	public ArrayList<String> getNamesArrays(InputStream stream) {
		ArrayList<String> namesString = new ArrayList<String>();
		try {
			StringBuilder buffe = new StringBuilder();

			InputStream json = stream;
			BufferedReader in = new BufferedReader(new InputStreamReader(json));
			String string;
			while ((string = in.readLine()) != null) {
				buffe.append(string);
			}

			in.close();
			String html = buffe.toString();
			Document doc = Jsoup.parse(html, "", Parser.xmlParser());

			for (Element e : doc.select("SimpleData")) {
				if (e.attr("name").equalsIgnoreCase("TYPE")) {
					namesString.add(e.toString()
							.replace("<simpledata name=\"TYPE\">", "")
							.replace("</simpledata>", ""));
				}
			}
		} catch (Exception f) {
			f.printStackTrace();
		}

		return namesString;

	}

}
