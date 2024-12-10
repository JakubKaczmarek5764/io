package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class Repository {
    private static EntityManagerFactory entityManagerFactory  = Persistence.createEntityManagerFactory("default");

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    static <T> void add(T entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    static <T> void remove(Class<T> entityClass, long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            em.remove(entity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    static <T> void update(T entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
        static <T> T get(Class<T> entityClass, long id) {
            EntityManager em = entityManagerFactory.createEntityManager();
            T entity = null;
            try {
                em.getTransaction().begin();
                entity = em.find(entityClass, id);
                em.getTransaction().commit();
                em.close();
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
            return entity;
        }
        static <T> List<T> getAll(Class<T> entityClass) {
            EntityManager em = entityManagerFactory.createEntityManager();
            List<T> entities = null;
            try {
                em.getTransaction().begin();
                entities = em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
                em.getTransaction().commit();
                em.close();
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
            return entities;
        }
}
