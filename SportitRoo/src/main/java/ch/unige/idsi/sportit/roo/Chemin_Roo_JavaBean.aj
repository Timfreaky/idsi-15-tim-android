// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.sportit.roo;

import ch.unige.idsi.sportit.roo.Chemin;

privileged aspect Chemin_Roo_JavaBean {
    
    public String Chemin.getNom() {
        return this.nom;
    }
    
    public void Chemin.setNom(String nom) {
        this.nom = nom;
    }
    
}
