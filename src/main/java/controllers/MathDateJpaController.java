package controllers;

import controllers.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import math.MathDate;

import java.io.Serializable;
import java.util.List;

/**
 * Class is used as MathDate controller. It allows to perform multiple
 * operations on database entity without using sql queries.
 *
 * @author Marek Kawalski
 * @version 1.0
 */
public class MathDateJpaController implements Serializable {

    public MathDateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Create new item in the table
     *
     * @param mathDate entity which contains calculations
     */
    public void create(MathDate mathDate) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mathDate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Perform update on table
     *
     * @param mathDate entity which contains calculations
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(MathDate mathDate) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mathDate = em.merge(mathDate);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = mathDate.getId();
                if (findMathDate(id) == null) {
                    throw new NonexistentEntityException("The mathDate with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method is used to delete objects from table
     *
     * @param id object to be removed
     * @throws NonexistentEntityException
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MathDate mathDate;
            try {
                mathDate = em.getReference(MathDate.class, id);
                mathDate.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mathDate with id " + id + " no longer exists.", enfe);
            }
            em.remove(mathDate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method returns all elements from table
     *
     * @return all elements from table
     */
    public List<MathDate> findMathDateEntities() {
        return findMathDateEntities(true, -1, -1);
    }

    /**
     * Method returns particular elements of the table
     *
     * @param maxResults
     * @param firstResult
     * @return elements of the table
     */
    public List<MathDate> findMathDateEntities(int maxResults, int firstResult) {
        return findMathDateEntities(false, maxResults, firstResult);
    }

    /**
     * /**
     * Method allows to find elements of Math Entity..
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return list of objects
     */
    private List<MathDate> findMathDateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MathDate.class));
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
     * Method allows to find specyfic element using its id.
     *
     * @param id special, unique id
     * @return object which we are looking for
     */
    public MathDate findMathDate(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MathDate.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Method returns amount of items in a table
     *
     * @return items count
     */
    public int getMathDateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MathDate> rt = cq.from(MathDate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
