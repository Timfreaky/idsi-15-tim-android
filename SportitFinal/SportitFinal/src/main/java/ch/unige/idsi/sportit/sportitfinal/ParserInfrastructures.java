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

		// Reprise du document kml à parser
		URL urlkml = new URL("http://sportitfinal.cfapps.io/doc_inf.kml");
		URLConnection uc = urlkml.openConnection();
		uc.connect();

		// Lecture du document
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

		// Pour chaque élément, on reprend son nom
		ArrayList<String> nameString = new ArrayList<String>();

		for (Element es : doc.select("SimpleData")) {
			if (es.attr("name").equalsIgnoreCase("TYPE")) {
				nameString.add(es.toString()
						.replace("<simpledata name=\"TYPE\">", "")
						.replace("</simpledata>", ""));
			}
		}
		
		/*
		for (int i = 0; i < nameString.size(); i++) {
			System.out.println("Name: "+nameString.get(i) + "\n");
		}*/
		

		// Pour chaque élément, on reprend les coordonnées dans une arrayList
		// (latitude et longitude)		
		ArrayList<String> tracksString = new ArrayList<String>();

		for (Element ec : doc.select("coordinates")) {
			tracksString.add(ec.toString().replace("<coordinates>", "")
					.replace("</coordinates>", ""));
		}

		// ArrayList de latitude
		ArrayList<Double> lat = new ArrayList<Double>();
		ArrayList<Double> longi = new ArrayList<Double>();

		for (int i = 0; i < tracksString.size(); i++) {
			// Reprise des coordonnées de chaque infrastructure dans une
			// arraylist pour pouvoir les séparer

			ArrayList<String> onePlaceString = new ArrayList<String>(
					Arrays.asList(tracksString.get(i).split("\\s+")));
			for (int k = 1; k < onePlaceString.size(); k++) {
				// On split la coordonnée en latitude et en longitude
				// Insertion de la latitude dans l'objet infrastrcutre
				lat.add(Double.parseDouble(onePlaceString.get(k).split(",")[0]));
				// Insertion de la longitude dans l'infrastructures
				// longitudeInfra =
				longi.add(Double
						.parseDouble(onePlaceString.get(k).split(",")[1]));
			}
		}

		//OK!
		/*
		for (int i = 0; i< lat.size(); i++){
			System.out.println("Lat: "+ lat.get(i));
		}
		for (int i = 0; i< longi.size(); i++){
			System.out.println("Long: "+ longi.get(i));
		}*/
		
		for (int i = 0; i < nameString.size(); i++) {
			//System.out.println("Name: "+nameString.get(i) + " lat: "+ lat.get(i) + " long: " + longi.get(i) );	
			//Toutes les listes doivent avoir la même longeur car pour un nom, une lat et une long!
			// déclaration de mon objet infrastructure
			Infrastructures infrastructure = new Infrastructures();
			infrastructure.setLatitude(lat.get(i));
			infrastructure.setLongitude(longi.get(i));
			infrastructure.setName(nameString.get(i));
			infrastructure.persist();	
		}
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/adresse.jsp" ).forward( request, response );
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