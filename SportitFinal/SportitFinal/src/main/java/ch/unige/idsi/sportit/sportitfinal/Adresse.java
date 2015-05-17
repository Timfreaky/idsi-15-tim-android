package ch.unige.idsi.sportit.sportitfinal;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Adresse {

    /**
     */
    private String adresse;

    /**
     */
    private Double latitude;

    /**
     */
    private Double longitude;
}
