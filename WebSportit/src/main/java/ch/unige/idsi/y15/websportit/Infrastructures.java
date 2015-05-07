package ch.unige.idsi.y15.websportit;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Infrastructures {

    /**
     */
    private String Name;

    /**
     */
    private double Latitude;

    /**
     */
    private double Longitude;
}
