package ch.unige.idsi.sportit.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.unige.idsi.sportit.beans.BeanInfrastructure;
import javafx.scene.chart.PieChart.Data;

public class BeansInfra {

String csvFile = "UNI_INSTA_SPORT_LIEUX.csv";
BufferedReader br = null;
String line = "";

	List<BeanInfrastructure> infrastructures = new ArrayList<BeanInfrastructure>();
	try {
 
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
 
		        // use comma as separator
			String[] splitted = line.split(";");
			
			BeanInfrastructure infrastructure = new BeanInfrastructure();
			infrastructure.setnomInfra(splitted[0]);
			infrastructure.setlatitude(splitted[9]);
			infrastructure.setlongitude(splitted[10]);
			
			infrastructures.add(infrastructure);
 
		}
	}
}
	