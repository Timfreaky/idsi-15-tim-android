package ch.unige.idsi.sportit.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletLocation extends HttpServlet {
	public static final String champs_rue = "rue";
    public static final String champs_numero = "numero";
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/affichageLocation.jsp")
				.forward(request, response);

	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		/* Traitement des données du formulaire */
		String resultat;
        Map<String, String> erreurs = new HashMap<String, String>();
        
		String rue = request.getParameter( champs_rue );

        try {
            validationRue( rue );
        } catch (Exception e) {
            erreurs.put (champs_rue, e.getMessage());
        }	
        
        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";
        }
        
        /* Stockage du résultat et des messages d'erreur dans l'objet request */
        request.setAttribute( ATT_ERREURS, erreurs );
        request.setAttribute( ATT_RESULTAT, resultat );

        /* Transmission de la paire d'objets request/response à notre JSP */
        this.getServletContext().getRequestDispatcher( "/WEB-INF/affichageLocation.jsp" ).forward( request, response );
	}
	private void validationRue( String rue ) throws Exception{
		if ( rue != null){
	        throw new Exception( "Merci de saisir une rue." );
	    }
	}
}
