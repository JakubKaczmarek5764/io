package db;
import Classes.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

import static java.util.List.of;

public class UsersRepository implements CRUDManager<User> {

    private static EntityManagerFactory entityManagerFactory;

    private static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public UsersRepository() {
        init();
    }

    @Override
    public void add(User entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace(); //todo: zmienic to potem
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(Class<User> entityClass, long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            User entity = em.find(entityClass, id);
            em.remove(entity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace();  //todo: zmienic to potem
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace(); //todo: zmienic to potem
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public User get(Class<User> entityClass, long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        User entity = null;
        try {
            em.getTransaction().begin();
            entity = em.find(entityClass, id);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace(); //todo: zmienic to potem
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public List<User> getAll(Class<User> entityClass) {
        // todo: w klasie bazowej dodac query w adnotacjach ktore tu bedzie wywolywane
        return List.of();
    }
}
