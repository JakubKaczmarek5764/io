package db;

import Classes.Task;
import Classes.Volunteer;
import Classes.volunteerEvaluation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class VolunteerRepository implements CRUDManager<Volunteer> {

    private EntityManager em;

    @Override
    public void add(Volunteer entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(Class<Volunteer> entityClass, long id) {
        Repository.remove(entityClass, id);
    }

    @Override
    public void update(Volunteer entity) {
        Repository.update(entity);
    }

    @Override
    public Volunteer get(Class<Volunteer> entityClass, long id) {
        return Repository.get(entityClass, id);
    }

    @Override
    public List<Volunteer> getAll(Class<Volunteer> entityClass) {
        return Repository.getAll(entityClass);
    }

    public void assignTaskToVolunteer(Volunteer volunteer, Task task) {
        EntityManager em = Repository.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            if (volunteer.isAvailable()) {
                volunteer.setCurrentTask(task);
                volunteer.setAvailable(false);
                task.setVolunteer(volunteer);
                em.merge(volunteer);
                em.merge(task);
                em.getTransaction().commit();
            } else {
                throw new IllegalStateException("Volunteer is not available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    public List<Task> getAllTasks() {
        return Repository.getAll(Task.class);
    }

    public Task getTask(int taskId) {
        Task task = Repository.get(Task.class, taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found.");
        }
        return task;
    }

    public void evaluateVolunteer(int volunteerId, int taskId, int rating, String description) {
        Volunteer volunteer = Repository.get(Volunteer.class, volunteerId);
        Task task = Repository.get(Task.class, taskId);

        if (volunteer == null || task == null) {
            throw new IllegalArgumentException("Volunteer or Task not found.");
        }

        if (task.getVolunteer() == null || task.getVolunteer().getVolunteerId() != volunteerId) {
            throw new IllegalStateException("The task is not assigned to the volunteer.");
        }
        EntityManager em = Repository.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            volunteerEvaluation evaluation = new volunteerEvaluation();
            evaluation.setVolunteer(volunteer);
            evaluation.setTask(task);
            evaluation.setRating(rating);
            evaluation.setDescription(description);
            evaluation.setEvaluationDate(new Date());

            em.persist(evaluation);

            task.setStatus("Completed");
            task.setCompletedDate(new Date());
            em.merge(task);

            volunteer.setAvailable(true);
            volunteer.setCurrentTask(null);
            em.merge(volunteer);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    public List<Volunteer> getAvailableVolunteers() {
        TypedQuery<Volunteer> query = em.createQuery("SELECT v FROM Volunteer v WHERE v.available = true", Volunteer.class);
        return query.getResultList();
    }

    public void addTask(Task task) {
        Repository.add(task);
    }

    public void removeTask(long taskId) {
        Repository.remove(Task.class, taskId);
    }

    public void updateTask(Task task) {
        Repository.update(task);
    }

    public Task getTaskById(long taskId) {
        return Repository.get(Task.class, taskId);
    }
}

