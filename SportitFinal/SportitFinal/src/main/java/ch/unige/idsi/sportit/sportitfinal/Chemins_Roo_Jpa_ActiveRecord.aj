// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.sportit.sportitfinal;

import ch.unige.idsi.sportit.sportitfinal.Chemins;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Chemins_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Chemins.entityManager;
    
    public static final List<String> Chemins.fieldNames4OrderClauseFilter = java.util.Arrays.asList("points");
    
    public static final EntityManager Chemins.entityManager() {
        EntityManager em = new Chemins().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Chemins.countCheminses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Chemins o", Long.class).getSingleResult();
    }
    
    public static List<Chemins> Chemins.findAllCheminses() {
        return entityManager().createQuery("SELECT o FROM Chemins o", Chemins.class).getResultList();
    }
    
    public static List<Chemins> Chemins.findAllCheminses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Chemins o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Chemins.class).getResultList();
    }
    
    public static Chemins Chemins.findChemins(Long id) {
        if (id == null) return null;
        return entityManager().find(Chemins.class, id);
    }
    
    public static List<Chemins> Chemins.findCheminsEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Chemins o", Chemins.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Chemins> Chemins.findCheminsEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Chemins o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Chemins.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Chemins.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Chemins.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Chemins attached = Chemins.findChemins(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Chemins.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Chemins.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Chemins Chemins.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Chemins merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
