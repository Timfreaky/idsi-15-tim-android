package ch.unige.idsi.sportit;

/**
 * @author Timothy McGarry & Florine Monnier
 * @version 0.1
 */

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

public class PathParser {

	public PathParser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<ArrayList<LatLng>> getCoordinateArrays(InputStream stream) {
		ArrayList<ArrayList<LatLng>> allTracks = new ArrayList<ArrayList<LatLng>>();

		try {
			StringBuilder buf = new StringBuilder();

			InputStream json = stream;
			BufferedReader in = new BufferedReader(new InputStreamReader(json));
			String str;
			String buffer;
			while ((str = in.readLine()) != null) {
				buf.append(str);
			}

			in.close();
			String html = buf.toString();
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
		// System.out.println(allTracks);
		return allTracks;

	}

}
