// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.sportit.sportitfinal;

import ch.unige.idsi.sportit.sportitfinal.Adresse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Adresse_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Adresse.entityManager;
    
    public static final List<String> Adresse.fieldNames4OrderClauseFilter = java.util.Arrays.asList("adresse", "latitude", "longitude");
    
    public static final EntityManager Adresse.entityManager() {
        EntityManager em = new Adresse().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Adresse.countAdresses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Adresse o", Long.class).getSingleResult();
    }
    
    public static List<Adresse> Adresse.findAllAdresses() {
        return entityManager().createQuery("SELECT o FROM Adresse o", Adresse.class).getResultList();
    }
    
    public static List<Adresse> Adresse.findAllAdresses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Adresse o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Adresse.class).getResultList();
    }
    
    public static Adresse Adresse.findAdresse(Long id) {
        if (id == null) return null;
        return entityManager().find(Adresse.class, id);
    }
    
    public static List<Adresse> Adresse.findAdresseEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Adresse o", Adresse.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Adresse> Adresse.findAdresseEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Adresse o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Adresse.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Adresse.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Adresse.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Adresse attached = Adresse.findAdresse(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Adresse.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Adresse.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Adresse Adresse.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Adresse merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
