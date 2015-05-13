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

import ch.unige.idsi.sportit.LatLng;
import ch.unige.idsi.sportit.String;

/**
 * Servlet implementation class ParserChemins
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		ArrayList<ArrayList<ArrayList<Double>>> ensembleChemins = new ArrayList<ArrayList<ArrayList<Double>>>();

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
		for (int i = 0; i < tracksString.size(); i++) {
            ArrayList<Double> lat = new ArrayList<Double>();
            ArrayList<Double> longi = new ArrayList<Double>();
            ArrayList<ArrayList<Double>> unChemin = new ArrayList<ArrayList<Double>>();
            ArrayList<String> oneTrackString = new ArrayList<String>(Arrays.asList(tracksString.get(i).split("\\s+")));
            for (int k = 1; k < oneTrackString.size(); k++) {
                lat.add(Double.parseDouble(oneTrackString.get(k).split(",")[0]));
                longi.add(Double.parseDouble(oneTrackString.get(k).split(",")[1]));
                latsUnChemin.add(lat);
                longUnChemin.add(longi);
            }
            ensembleChemins.add(unChemin);
        }
		
		Chemins chemin = new Chemins();
		
		for (int i=0; i<ensembleChemins.size();i++){
			chemin.persist();
			chemin.setPoints();
		}

		// Un chemin est une collection de points
		/*
		ArrayList<ArrayList<Double>> cheminPoint = new
		ArrayList<ArrayList<Double>>();

		for (int i = 0; i < tracksString.size(); i++) {

			// Un point est une collection de double [lat, lng]
			ArrayList<Double> point = new ArrayList<Double>();

			ArrayList<String> oneTrackString = new ArrayList<String>(
					Arrays.asList(tracksString.get(i).split("\\s+")));
			for (int k = 1; k < oneTrackString.size(); k++) {

				ArrayList<String> oneTrackStringC = new ArrayList<String>(
				// Les divers points sont séparés par des ",0"
						Arrays.asList(oneTrackString.get(k).split(",0")));
				for (int l = 1; l < oneTrackStringC.size(); l++) {
					// latitude
					double lat = Double.parseDouble(oneTrackString.get(l)
							.split(",")[0]);
					// longitude
					double lng = Double.parseDouble(oneTrackString.get(l)
							.split(",")[1]);
					point.add(lat);
					point.add(lng);
					System.out.println("point: " + point);
				}
				cheminPoint.add(point);
			}
		}
		System.out.println("CheminPoint: " + cheminPoint);
		 //chemin.setPoints(cheminPoint);*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
