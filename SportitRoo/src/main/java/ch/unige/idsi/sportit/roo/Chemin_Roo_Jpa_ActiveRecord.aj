// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.sportit.roo;

import ch.unige.idsi.sportit.roo.Chemin;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Chemin_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Chemin.entityManager;
    
    public static final List<String> Chemin.fieldNames4OrderClauseFilter = java.util.Arrays.asList("nom");
    
    public static final EntityManager Chemin.entityManager() {
        EntityManager em = new Chemin().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Chemin.countChemins() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Chemin o", Long.class).getSingleResult();
    }
    
    public static List<Chemin> Chemin.findAllChemins() {
        return entityManager().createQuery("SELECT o FROM Chemin o", Chemin.class).getResultList();
    }
    
    public static List<Chemin> Chemin.findAllChemins(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Chemin o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Chemin.class).getResultList();
    }
    
    public static Chemin Chemin.findChemin(Long id) {
        if (id == null) return null;
        return entityManager().find(Chemin.class, id);
    }
    
    public static List<Chemin> Chemin.findCheminEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Chemin o", Chemin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Chemin> Chemin.findCheminEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Chemin o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Chemin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Chemin.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Chemin.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Chemin attached = Chemin.findChemin(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Chemin.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Chemin.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Chemin Chemin.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Chemin merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
