// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.sportit.sportitfinal;

import ch.unige.idsi.sportit.sportitfinal.Adresse;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Adresse_Roo_Jpa_Entity {
    
    declare @type: Adresse: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Adresse.id;
    
    @Version
    @Column(name = "version")
    private Integer Adresse.version;
    
    public Long Adresse.getId() {
        return this.id;
    }
    
    public void Adresse.setId(Long id) {
        this.id = id;
    }
    
    public Integer Adresse.getVersion() {
        return this.version;
    }
    
    public void Adresse.setVersion(Integer version) {
        this.version = version;
    }
    
}
