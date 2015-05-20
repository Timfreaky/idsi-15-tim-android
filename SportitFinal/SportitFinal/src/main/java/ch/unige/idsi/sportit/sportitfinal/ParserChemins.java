package ch.unige.idsi.sportit.sportitfinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

/**
 * Servlet implementation class ParserChemins
 * Cette servlet parse le kml pour reprendre les chemins
 * 
 * @author Florine et Tim
 * @version 1.0
 */
public class ParserChemins extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ParserChemins() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * Deux arrayList sont créées pour contenir la liste des latitudes d'un chemins et la liste des longitudes
	 * Le kml est parsé pour reprendre les coordonnées
	 * des objets de chemins sont insérés dans le model Chemin grâce à 
	 * Chemins chemin = new Chemins();
			chemin.setLatitude(allTracksLatitude.get(i));
			chemin.setLongitude(allTracksLongitude.get(i));
			chemin.persist();
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// Reprise du document kml à parser
		URL urlkml = new URL("http://sportitfinal.cfapps.io/doc.kml");
		URLConnection uc = urlkml.openConnection();
		uc.connect();
		StringBuilder buff = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlkml.openStream()));
		String string;
		while ((string = in.readLine()) != null) {
			buff.append(string);
		}
		in.close();
		String html = buff.toString();
		Document doc = Jsoup.parse(html, "", Parser.xmlParser());
		ArrayList<String> tracksString = new ArrayList<String>();
		for (Element e : doc.select("coordinates")) {
			tracksString.add(e.toString().replace("<coordinates>", "")
					.replace("</coordinates>", ""));
		}
		//Déclaration des listes de latitudes et de longitudes
		ArrayList<ArrayList<Double>> allTracksLatitude = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> allTracksLongitude = new ArrayList<ArrayList<Double>>();
		
		for (int i = 0; i < tracksString.size(); i++) {
			ArrayList<Double> oneTrackLatitude = new ArrayList<Double>();
			ArrayList<Double> oneTrackLongitude = new ArrayList<Double>();

			ArrayList<String> oneTrackString = new ArrayList<String>(
					Arrays.asList(tracksString.get(i).split("\\s+")));
			for (int k = 1; k < oneTrackString.size(); k++) {
				MonLatLng latLng = new MonLatLng(Double.parseDouble(oneTrackString
						.get(k).split(",")[0]),
						Double.parseDouble(oneTrackString.get(k).split(",")[1]));
				oneTrackLatitude.add(latLng.getLatitude());
				oneTrackLongitude.add(latLng.getLongitude());
			}
			allTracksLatitude.add(oneTrackLatitude);
			allTracksLongitude.add(oneTrackLongitude);
		}

		for (int i = 0; i < allTracksLatitude.size(); i++) {
			Chemins chemin = new Chemins();
			chemin.setLatitude(allTracksLatitude.get(i));
			chemin.setLongitude(allTracksLongitude.get(i));
			chemin.persist();
		
		}
	}

}
