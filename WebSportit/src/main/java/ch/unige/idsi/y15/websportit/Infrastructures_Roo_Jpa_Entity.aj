// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.y15.websportit;

import ch.unige.idsi.y15.websportit.Infrastructures;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Infrastructures_Roo_Jpa_Entity {
    
    declare @type: Infrastructures: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Infrastructures.id;
    
    @Version
    @Column(name = "version")
    private Integer Infrastructures.version;
    
    public Long Infrastructures.getId() {
        return this.id;
    }
    
    public void Infrastructures.setId(Long id) {
        this.id = id;
    }
    
    public Integer Infrastructures.getVersion() {
        return this.version;
    }
    
    public void Infrastructures.setVersion(Integer version) {
        this.version = version;
    }
    
}
