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

import com.google.maps.model.LatLng;

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

		ArrayList<ArrayList<LatLng>> allTracks = new ArrayList<ArrayList<LatLng>>();

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
			ArrayList<LatLng> oneTrack = new ArrayList<LatLng>();

			ArrayList<String> oneTrackString = new ArrayList<String>(
					Arrays.asList(tracksString.get(i).split("\\s+")));
			for (int k = 1; k < oneTrackString.size(); k++) {
				LatLng latLng = new LatLng(Double.parseDouble(oneTrackString
						.get(k).split(",")[0]),
						Double.parseDouble(oneTrackString.get(k).split(",")[1]));
				oneTrack.add(latLng);
			}
			allTracks.add(oneTrack);
		}

		Chemins chemins = new Chemins();
		chemins.persist();
		for (int i = 0; i < allTracks.size(); i++) {
			chemins.setChemin(allTracks.get(i));
			System.out.println(allTracks.get(i));
		}
		//System.out.println("Chemin: "+chemins.getChemins());
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
