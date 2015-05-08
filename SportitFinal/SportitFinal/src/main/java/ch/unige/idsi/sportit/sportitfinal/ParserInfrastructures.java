package ch.unige.idsi.sportit.sportitfinal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

	
		//Test d'impression
		/*URL urlkml = new URL("http://sportitfinal.cfapps.io/doc_inf.kml");
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
	        bw.close();*/

		//Reprise du kml du serveur par son URL
		URL urlkml = new URL("http://sportitfinal.cfapps.io/doc_inf.kml");
		URLConnection uc = urlkml.openConnection();
		uc.connect();
		InputStream in = uc.getInputStream();
		//Création d'une File a parser
		FileOutputStream out = new FileOutputStream("kmlFile.kml");
		final int BUF_SIZE = 1 << 8;
		byte[] buffer = new byte[BUF_SIZE];
		int bytesRead = -1;
		while((bytesRead = in.read(buffer)) > -1) {
		    out.write(buffer, 0, bytesRead);
		}
		in.close();
		out.close();
	    
	        final Kml kml = Kml.unmarshal("kmlFile.kml");

			final Document document = (Document) kml.getFeature();
			System.out.println(document.getName());
	
	    //Parsage du kml
		List<Feature> t = document.getFeature();
        for(Object o : t){
            Folder f = (Folder)o;
            List<Feature> tg = f.getFeature();
            for(Object ftg : tg){
            	//Reprise du nom
                Placemark g = (Placemark) ftg;
                System.out.println(g.getName());                
                
                //Reprise des coordonnées
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
	

    @Transactional
    public void onApplicationEvent(String name, String latitude, String longitude) {
    	//Insersion des données dans la base de données des Infrastructures créée avec Roo
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
