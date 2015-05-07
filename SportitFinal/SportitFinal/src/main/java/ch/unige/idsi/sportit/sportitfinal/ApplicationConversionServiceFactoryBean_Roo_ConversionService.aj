// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.sportit.sportitfinal;

import ch.unige.idsi.sportit.sportitfinal.ApplicationConversionServiceFactoryBean;
import ch.unige.idsi.sportit.sportitfinal.Infrastructures;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Infrastructures, String> ApplicationConversionServiceFactoryBean.getInfrastructuresToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ch.unige.idsi.sportit.sportitfinal.Infrastructures, java.lang.String>() {
            public String convert(Infrastructures infrastructures) {
                return new StringBuilder().append(infrastructures.getName()).append(' ').append(infrastructures.getLatitude()).append(' ').append(infrastructures.getLongitude()).toString();
            }
        };
    }
    
    public Converter<Long, Infrastructures> ApplicationConversionServiceFactoryBean.getIdToInfrastructuresConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ch.unige.idsi.sportit.sportitfinal.Infrastructures>() {
            public ch.unige.idsi.sportit.sportitfinal.Infrastructures convert(java.lang.Long id) {
                return Infrastructures.findInfrastructures(id);
            }
        };
    }
    
    public Converter<String, Infrastructures> ApplicationConversionServiceFactoryBean.getStringToInfrastructuresConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ch.unige.idsi.sportit.sportitfinal.Infrastructures>() {
            public ch.unige.idsi.sportit.sportitfinal.Infrastructures convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Infrastructures.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getInfrastructuresToStringConverter());
        registry.addConverter(getIdToInfrastructuresConverter());
        registry.addConverter(getStringToInfrastructuresConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
