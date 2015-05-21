package ch.unige.idsi.sportit.sportitfinal;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
/**
 * 
 * Model pour les Infrastructures
 * 
 * @author Florine et Tim
 * @version 1.0
 *
 */
public class Infrastructures {

    private String name;

    /**
     */
    private double latitude;

    /**
     */
    private double longitude;
    
}
