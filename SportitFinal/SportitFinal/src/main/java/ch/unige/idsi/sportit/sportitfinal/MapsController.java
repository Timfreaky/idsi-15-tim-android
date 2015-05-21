package ch.unige.idsi.sportit.sportitfinal;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/maps/**")
@Controller
/**
 * Ce controller de la vue maps a pour but de lui envoyer les infrastructures sportives, 
 * les chemins pédestres et l'adresse de l'utilisateur pour modifier celle-ci en conséquence
 * 
 * @author Florine et Tim
 * @version 1.0
 *
 */
public class MapsController {

	@RequestMapping
	/**
	 * Cette méthode retourne à la maps les infrastructures, les chemins et l'adresse de l'utilisateur à afficher
	 * Pour cela, il faut lister les infrastructures pour pouvoir les envoyer, comme pour les chemins et l'adresse (qui est unique car il n'y en a qu'une)
	 * La fonction model.addAttribute envoie les informations à la jsp maps/index
	 * @param model
	 * @return
	 */
	public String index(final ModelMap model) {

		/*String markersInfra = "";
		List<Infrastructures> infrastructures = Infrastructures
				.findAllInfrastructureses();
		for (Iterator<Infrastructures> i = infrastructures.iterator(); i
				.hasNext();) {
			Infrastructures infrastructure = (Infrastructures) i.next();
			markersInfra += "['" + infrastructure.getName() + "',"
					+ infrastructure.getLatitude() + ","
					+ infrastructure.getLongitude() + "],";
		}
		markersInfra += "[' ',0,0]";

		System.out.println("Markers Infra in Controller: "+markersInfra);
		model.addAttribute("markerInfra", markersInfra);*/
		
		List<Infrastructures> infrastructures = Infrastructures.findInfrastructuresEntries(0, 5);
		Infrastructures infrastructure; 
			//for (int i=0; i<=4;i++){
				infrastructure = infrastructures.get(0);
				String nameInfra = infrastructure.getName();
				model.addAttribute("nameInfra0", nameInfra);
				Double latInfra = infrastructure.getLatitude();
				model.addAttribute("latInfra0", latInfra);
				Double lngInfra = infrastructure.getLongitude();
				model.addAttribute("lngInfra0", lngInfra);
				System.out.println(nameInfra + latInfra + lngInfra);
				infrastructure = infrastructures.get(1);
				String nameInfra1 = infrastructure.getName();
				model.addAttribute("nameInfra1", nameInfra1);
				Double latInfra1 = infrastructure.getLatitude();
				model.addAttribute("latInfra1", latInfra1);
				Double lngInfra1 = infrastructure.getLongitude();
				model.addAttribute("lngInfra1", lngInfra1);
				System.out.println(nameInfra1 + latInfra1 + lngInfra1);
				String nameInfra2 = infrastructure.getName();
				model.addAttribute("nameInfra2", nameInfra2);
				Double latInfra2 = infrastructure.getLatitude();
				model.addAttribute("latInfra2", latInfra2);
				Double lngInfra2 = infrastructure.getLongitude();
				model.addAttribute("lngInfra2", lngInfra2);
				System.out.println(nameInfra2 + latInfra2 + lngInfra2);
				String nameInfra3 = infrastructure.getName();
				model.addAttribute("nameInfra2", nameInfra3);
				Double latInfra3 = infrastructure.getLatitude();
				model.addAttribute("latInfra3", latInfra3);
				Double lngInfra3 = infrastructure.getLongitude();
				model.addAttribute("lngInfra3", lngInfra3);
				System.out.println(nameInfra3 + latInfra3 + lngInfra3);
				String nameInfra4 = infrastructure.getName();
				model.addAttribute("nameInfra4", nameInfra4);
				Double latInfra4 = infrastructure.getLatitude();
				model.addAttribute("latInfra4", latInfra4);
				Double lngInfra4 = infrastructure.getLongitude();
				model.addAttribute("lngInfra4", lngInfra4);
				System.out.println(nameInfra4 + latInfra4 + lngInfra4);
				
			//}
		//List<Infrastructures> infrastructure2 = Infrastructures.findInfrastructures(1);
		
			/*
		String chemin = "[";
		List<Chemins> chemins = Chemins.findAllCheminses();
		for (Iterator<Chemins> it = chemins.iterator(); it.hasNext();) {
			Chemins chem = (Chemins) it.next();

			List<Double> lat = chem.getLatitude();
			List<Double> lng = chem.getLongitude();

			chemin += "[";
			for (int i = 0; i < lat.size(); i++) {
				Double lati = lat.get(i);
				Double longi = lng.get(i);
				chemin += "new google.maps.LatLng(" + lati + "," + longi + "),";
			}
			chemin += "new google.maps.LatLng(0,0)]\n";
		}
		chemin += "]";
		
		System.out.println(chemin);
		model.addAttribute("cheminsPedestres", chemin);*/

		List<Adresse> adresse = Adresse.findAllAdresses();
		for (Iterator<Adresse> ite = adresse.iterator(); ite.hasNext();) {
			Adresse ad = (Adresse) ite.next();
			Double latUtil = ad.getLatitude();
			Double longUtil = ad.getLongitude();
			model.addAttribute("latitudeUtilisateur", latUtil);
			model.addAttribute("longitudeUtilisateur", longUtil);
			System.out.println("Localisation utilisateur: "+latUtil + " , " + longUtil );
		}
		return "maps/index";
	}
}