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
 * Servlet implementation class ParserInfrastructures
 */
public class ParserInfrastructures extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ParserInfrastructures() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// déclaration de mon objet infrastructure
		Infrastructures infrastructures = new Infrastructures();
		// enregistrement des infrastructures
		infrastructures.persist();

		// Reprise du document kml à parser
		URL urlkml = new URL("http://sportitfinal.cfapps.io/doc_inf.kml");
		URLConnection uc = urlkml.openConnection();
		uc.connect();
		StringBuilder buff = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlkml.openStream()));
		String string;
		while ((string = in.readLine()) != null) {
			buff.append(string);
			// System.out.println(string);
		}

		in.close();
		String html = buff.toString();
		// déclaration du document parsé
		Document doc = Jsoup.parse(html, "", Parser.xmlParser());

		/************* NOM INFRASTRUCTURES OK ***************************/
		// Pour chaque élément, on reprend son nom
		ArrayList<String> tracksStringName = new ArrayList<String>();
		String nomInfra = new String();
		for (Element es : doc.select("SimpleData")) {
			if (es.attr("name").equalsIgnoreCase("TYPE")) {
				tracksStringName.add(es.toString()
						.replace("<simpledata name=\"TYPE\">", "")
						.replace("</simpledata>", ""));

				// Ajout du nom dans l'objet infrastructures
				for (int i = 0; i < tracksStringName.size(); i++) {
					ArrayList<String> onePlaceName = new ArrayList<String>(
							Arrays.asList(tracksStringName.get(i).split("\\s+")));
					for (int k = 1; k < onePlaceName.size(); k++) {
						nomInfra = onePlaceName.get(k);
					}
				}

				infrastructures.setName(nomInfra);
				System.out.println("nom " + nomInfra);
			}
		}

		/******************** PROBLEME AVEC LAT ET LONG ******************/
		/********************PLUSIEURS LAT OU LONG DE SUITE***************/
		// Pour chaque élément, on reprend les coordonnées dans une arrayList
		// (latitude et longitude)
		ArrayList<String> tracksString = new ArrayList<String>();
		Double latitudeInfra = null;
		Double longitudeInfra = null;
		for (Element ec : doc.select("coordinates")) {
			tracksString.add(ec.toString().replace("<coordinates>", "")
					.replace("</coordinates>", ""));
		}
		for (int i = 0; i < tracksString.size(); i++) {
			// Reprise des coordonnées de chaque infrastructure dans une
			// arraylist pour pouvoir les séparer
			ArrayList<String> onePlaceString = new ArrayList<String>(
					Arrays.asList(tracksString.get(i).split("\\s+")));
			for (int k = 1; k < onePlaceString.size(); k++) {
				// On split la coordonnée en latitude et en longitude
				// Insertion de la latitude dans l'objet infrastrcutre
				latitudeInfra = Double.parseDouble(onePlaceString.get(k).split(
						",")[0]);
				// Insertion de la longitude dans l'infrastructures
				longitudeInfra = Double.parseDouble(onePlaceString.get(k)
						.split(",")[1]);
			}
			infrastructures.setLatitude(latitudeInfra);
			infrastructures.setLongitude(longitudeInfra);
			System.out.println("Lat: " + latitudeInfra);
			System.out.println("Long: " + longitudeInfra);
		}

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