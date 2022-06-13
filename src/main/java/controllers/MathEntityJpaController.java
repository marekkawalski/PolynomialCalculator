package controllers;

import controllers.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import math.MathEntity;

import java.io.Serializable;
import java.util.List;

/**
 * This is a controller for MathEntity class. It allows to perform multiple
 * operations on database table without need to manually type sql queries.
 *
 * @author Marek Kawalski
 * @version 1.0
 */
public class MathEntityJpaController implements Serializable {

    public MathEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private final EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Method is used to push new items to a database table
     *
     * @param mathEntity object to be added to database table
     */
    public void create(MathEntity mathEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mathEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method is used to perform changes on table
     *
     * @param mathEntity object on which changes will be performed
     */
    public void edit(MathEntity mathEntity) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mathEntity = em.merge(mathEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = mathEntity.getId();
                if (findMathEntity(id) == null) {
                    throw new NonexistentEntityException("The mathEntity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    /**
     * Method is used to delete object from table.
     *
     * @param id id of element to be deleted
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MathEntity mathEntity;
            try {
                mathEntity = em.getReference(MathEntity.class, id);
                mathEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mathEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(mathEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method invokes another method and returns all elements from table
     *
     * @return all objects from table
     */
    public List<MathEntity> findMathEntityEntities() {
        return findMathEntityEntities(true, -1, -1);
    }

    /**
     * Method returns list of objects in the table.
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<MathEntity> findMathEntityEntities(int maxResults, int firstResult) {
        return findMathEntityEntities(false, maxResults, firstResult);
    }

    /**
     * Method returns list of objects in the table.
     *
     * @param all         return all results?
     * @param maxResults  max number of results
     * @param firstResult offset
     * @return list of objects we are looking for
     */
    private List<MathEntity> findMathEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MathEntity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Method finds element which has given id number
     *
     * @param id id of element we want to find
     * @return object we are looking for
     */
    public MathEntity findMathEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MathEntity.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Method returns amount of items in MathEntity table
     *
     * @return items count
     */
    public int getMathEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MathEntity> rt = cq.from(MathEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
