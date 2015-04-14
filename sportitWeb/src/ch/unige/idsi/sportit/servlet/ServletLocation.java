package ch.unige.idsi.sportit.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.unige.idsi.sportit.beans.BeansLocation;

public class ServletLocation extends HttpServlet {

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		BeansLocation adresse = new BeansLocation();
		/* Initialisation de ses propriétés */
		String rue = request.getParameter("rue");
		String numero = request.getParameter( "numero" );
		
		adresse.setRue(rue);
		adresse.setNumero(numero);
		
		request.setAttribute("adresse", adresse);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/affichageLocation.jsp" ).forward( request, response );
		
	}
}
