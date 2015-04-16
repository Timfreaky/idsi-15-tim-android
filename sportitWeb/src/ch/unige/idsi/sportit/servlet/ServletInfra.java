package ch.unige.idsi.sportit.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.unige.idsi.sportit.beans.BeanInfrastructure;

public class ServletInfra extends HttpServlet {

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		

	String csvFile = "UNI_INSTA_SPORT_LIEUX.csv";
	BufferedReader br = null;
	String line = "";
	ArrayList<BeanInfrastructure> infrastructures = new ArrayList<BeanInfrastructure>();
	
	
	try {
 
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
			String[] splitted = line.split(";");
			BeanInfrastructure infrastructure = new BeanInfrastructure();
			infrastructure.setnomInfra(splitted[0]);
			infrastructure.setlatitude(splitted[9]);
			infrastructure.setlongitude(splitted[10]);
			
			infrastructures.add(infrastructure);
 
		}
	}
	catch (Exception e){
		//some exception information
		}


	request.setAttribute("infrastructures", infrastructures);

	this.getServletContext().getRequestDispatcher( "/WEB-INF/affichageInfra.jsp" ).forward( request, response );

	}
}
	