package ch.unige.idsi.sportit.sportitfinal;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
/**
 * Cette classe est le model de l'adresse de l'utilisateur
 * 
 * @author Florine Monnier & Timothy McGarry
 * @version 1.0
 *
 */
public class Adresse {

    /**
     */
    private String adresse;

    /**
     */
    private double latitude;

    /**
     */
    private double longitude;
}
