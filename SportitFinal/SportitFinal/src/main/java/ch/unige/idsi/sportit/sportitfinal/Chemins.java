package ch.unige.idsi.sportit.sportitfinal;
import java.util.List;

import javax.persistence.ElementCollection;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Chemins {

    /**
     */
	@ElementCollection(targetClass=Double.class)
    private List<Double> latitude;

    /**
     */
	@ElementCollection(targetClass=Double.class)
    private List<Double> longitude;
}
