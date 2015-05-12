package ch.unige.idsi.sportit.sportitfinal;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.google.maps.model.LatLng;

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
