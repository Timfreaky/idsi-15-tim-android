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
		}

		in.close();
		String html = buff.toString();
		// déclaration du document parsé
		Document doc = Jsoup.parse(html, "", Parser.xmlParser());
		ArrayList<String> tracksString = new ArrayList<String>();

		// Pour chaque élément, on reprend son nom
		for (Element es : doc.select("SimpleData")) {
			if (es.attr("name").equalsIgnoreCase("TYPE")) {

				// Ajout du nom dans l'objet infrastructures
				infrastructures.setName(es.toString()
						.replace("<simpledata name=\"TYPE\">", "")
						.replace("</simpledata>", ""));
			}
		}
		// Pour chaque élément, on reprend ses coordonnées
		for (Element ec : doc.select("coordinates")) {
			tracksString.add(ec.toString().replace("<coordinates>", "")
					.replace("</coordinates>", ""));
		}
		for (int i = 0; i < tracksString.size(); i++) {

			ArrayList<String> onePlaceString = new ArrayList<String>(
					Arrays.asList(tracksString.get(i).split("\\s+")));
			for (int k = 1; k < onePlaceString.size(); k++) {
				// On split la coordonnée en latitude et en longitude
				// Insertion de la latitude dans l'objet infrastrcutre
				infrastructures.setLatitude(Double.parseDouble(onePlaceString
						.get(k).split(",")[0]));
				// Insertion de la longitude dans l'infrastructures
				infrastructures.setLongitude(Double.parseDouble(onePlaceString
						.get(k).split(",")[1]));
			}

		}
	}

	/*
	 * for (Element e : doc.select("coordinates")) {
	 * tracksString.add(e.toString().replace("<coordinates>",
	 * "").replace("</coordinates>", "")); }
	 * 
	 * for (int i = 0; i < tracksString.size(); i++) {
	 * 
	 * ArrayList<String> onePlaceString = new
	 * ArrayList<String>(Arrays.asList(tracksString.get(i).split("\\s+"))); for
	 * (int k = 1; k < onePlaceString.size(); k++) {
	 * infrastructures.setLatitude(Double.parseDouble
	 * (onePlaceString.get(k).split(",")[0]));
	 * infrastructures.setLongitude(Double.parseDouble
	 * (onePlaceString.get(k).split(",")[1])); }
	 * 
	 * }
	 * 
	 * for (Element e : doc.select("SimpleData")) {
	 * if(e.attr("name").equalsIgnoreCase("TYPE")){
	 * System.out.println(e.toString());
	 * infrastructures.setName(e.toString().replace
	 * ("<simpledata name=\"TYPE\">", "").replace("</simpledata>", "")); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * System.out.println(infrastructures);
	 */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}