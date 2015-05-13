package ch.unige.idsi.sportit.sportitfinal;
import java.util.ArrayList;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Infrastructures {

    private String name;

    /**
     */
    private double latitude;

    /**
     */
    private double longitude;
    
}
