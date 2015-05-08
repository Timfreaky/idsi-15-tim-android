package ch.unige.idsi.sportit.sportitfinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
System.out.println("process start...");

	
		URL urlkml = new URL("http://sportitfinal.cfapps.io/infrastructures.kml");
		BufferedReader in = new BufferedReader(
		new InputStreamReader(urlkml.openStream()));
		String inputLine;
		File file = new File("kmlFile.kml");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
	        while ((inputLine = in.readLine()) != null){
	            System.out.println(inputLine);
	            bw.write(inputLine);
	        }
	        in.close();
	        bw.close();
	    
	        final Kml kml = Kml.unmarshal("kmlFile.kml");
			System.out.println("process run");
			final Document document = (Document) kml.getFeature();
			System.out.println(document.getName());
	
	        
		List<Feature> t = document.getFeature();
        for(Object o : t){
            Folder f = (Folder)o;
            List<Feature> tg = f.getFeature();
            for(Object ftg : tg){
                Placemark g = (Placemark) ftg;
                System.out.println(g.getName());                
                
                Point point = (Point) g.getGeometry();
                List<Coordinate> coordinates = point.getCoordinates(); 
                for (Coordinate c : coordinates) 
                { 
                    System.out.println(c.getLatitude()); 
                    System.out.println(c.getLongitude()); 
                    
                    System.out.println("load loader function");    
                    onApplicationEvent(g.getName(), String.valueOf(c.getLatitude()), String.valueOf(c.getLongitude()));
                }  
                
                
            }
            
        }
	   
    
	}
	
//	void ParseKMZ(){
//		final Kml kml = Kml.unmarshal(new File("test.kmz"));
//		final Placemark placemark = (Placemark) kml.getFeature();
//		Point point = (Point) placemark.getGeometry();
//		List<Coordinate> coordinates = point.getCoordinates();
//		for (Coordinate coordinate : coordinates) {
//			System.out.println(coordinate.getLatitude());
//			System.out.println(coordinate.getLongitude());
//			System.out.println(coordinate.getAltitude());
//		}
//	}

    @Transactional
    public void onApplicationEvent(String name, String latitude, String longitude) {
        Infrastructures infrastructures = new Infrastructures();
        infrastructures.persist();
        infrastructures.setName(name);
        infrastructures.setLatitude(latitude);
        infrastructures.setLongitude(longitude);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
