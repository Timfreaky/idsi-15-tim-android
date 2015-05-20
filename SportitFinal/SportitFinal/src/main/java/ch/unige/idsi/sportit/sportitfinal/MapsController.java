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

		String markersInfra = "var infra = [";
		List<Infrastructures> infrastructures = Infrastructures
				.findAllInfrastructureses();
		for (Iterator<Infrastructures> i = infrastructures.iterator(); i
				.hasNext();) {
			Infrastructures infrastructure = (Infrastructures) i.next();
			markersInfra += "['" + infrastructure.getName() + "',"
					+ infrastructure.getLatitude() + ","
					+ infrastructure.getLongitude() + "],";
		}
		markersInfra += "[' ',0,0]];";

		// System.out.println(markersInfra);
		model.addAttribute("markerInfra", markersInfra);

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
		chemin += "];";

		model.addAttribute("cheminsPedestres", chemin);

		List<Adresse> adresse = Adresse.findAllAdresses();
		for (Iterator<Adresse> ite = adresse.iterator(); ite.hasNext();) {
			Adresse ad = (Adresse) ite.next();
			Double latUtil = ad.getLatitude();
			Double longUtil = ad.getLongitude();
			model.addAttribute("latitudeUtilisateur", latUtil);
			model.addAttribute("longitudeUtilisateur", longUtil);
		}

		// System.out.println(chemin);
		return "maps/index";
	}
}