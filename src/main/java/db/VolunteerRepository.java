package db;

import Classes.Task;
import Classes.Volunteer;
import Classes.VolunteerEvaluation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class VolunteerRepository implements IRepository<Volunteer> {

    private EntityManager em;

    @Override
    public void add(Volunteer entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Volunteer.class, id);
    }

    @Override
    public void update(Volunteer entity) {
        Repository.update(entity);
    }

    @Override
    public Volunteer get(long id) {
        return Repository.get(Volunteer.class, id);
    }

    @Override
    public List<Volunteer> getAll() {
        return Repository.getAll(Volunteer.class);
    }

    //todo: CZY PONIZSZE METODY NIE POWINNY BYC W LOGICE?

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

            VolunteerEvaluation evaluation = new VolunteerEvaluation();
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
}

