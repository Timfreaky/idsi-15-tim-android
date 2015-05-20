package ch.unige.idsi.sportit.sportitfinal;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/maps/**")
@Controller
public class MapsController {

    @RequestMapping
    //(method = RequestMethod.GET, value = "/map/index")
    public String index(final ModelMap model) {
    	
    	String markersInfra = "var infra = [";
    	List<Infrastructures> infrastructures = Infrastructures.findAllInfrastructureses();
		for (Iterator<Infrastructures> i = infrastructures.iterator(); i.hasNext();) {
			Infrastructures infrastructure = (Infrastructures) i.next();
			markersInfra += "['"+ infrastructure.getName() + "'," + infrastructure.getLatitude()+ "," + infrastructure.getLongitude()+"],";
		}
		markersInfra +="[' ',0,0]];";
		
		//System.out.println(markersInfra);
		model.addAttribute("markerInfra", markersInfra);
		
		/*
		String chemin = "";
		List<Chemins> chemins = Chemins.findAllCheminses();
			for (Iterator<Chemins> it = chemins.iterator(); it.hasNext();) {
				Chemins chem = (Chemins) it.next();
				
				List<Double> lat = chem.getLatitude();
				List<Double> lng = chem.getLongitude();
				
				chemin += "var chemLatLng = [";
					for (int i=0; i<lat.size(); i++){
						Double lati = lat.get(i);
						Double longi = lng.get(i);
						chemin += "new google.maps.LatLng("
								+ lati
								+ ","
								+ longi
								+")];";
					}
							}
			
    	model.addAttribute("cheminsPedestres",chemin);
    	System.out.println(chemin);*/
        return "maps/index";
    }
}
