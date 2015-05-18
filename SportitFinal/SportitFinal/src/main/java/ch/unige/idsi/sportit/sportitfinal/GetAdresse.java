package ch.unige.idsi.sportit.sportitfinal;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;

/**
 * Servlet implementation class getAdresse
 */
public class GetAdresse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAdresse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final Geocoder geocoder = new Geocoder();
		Adresse adresse = new Adresse();
		adresse.setAdresse(request.getParameter("adresse").toString());
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(adresse.getAdresse()).setLanguage("fr").getGeocoderRequest();
		//GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress("Carouge, Battelle").setLanguage("fr").getGeocoderRequest();
	    GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
	    List<GeocoderResult> someList = geocoderResponse.getResults();
	    GeocoderResult data = someList.get(0);
	    GeocoderGeometry data_2 = data.getGeometry();
	    //Double lat = (data_2.getLocation().getLat()).doubleValue();
	    //Double longi =(data_2.getLocation().getLng()).doubleValue();
	    //System.out.println("Lat: "+lat+"Long:" + longi);
	    adresse.setLatitude((data_2.getLocation().getLat()).doubleValue());
	    adresse.setLongitude((data_2.getLocation().getLng()).doubleValue());
	    adresse.persist();
	    
	    System.out.println("Votre adresse: "+ adresse.getAdresse() + ", latitude :" + adresse.getLatitude()+", longitude: "+ adresse.getLongitude());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}