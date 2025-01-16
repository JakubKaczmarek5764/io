package db;

import Classes.Task;
import Classes.status;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class TaskRepository implements IRepository<Task> {
    @Override
    public void add(Task entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Task.class, id);
    }

    @Override
    public void update(Task entity) {
        Repository.update(entity);
    }

    @Override
    public Task get(long id) {
        return Repository.get(Task.class, id);
    }

    @Override
    public List<Task> getAll() {
        return Repository.getAll(Task.class);
    }

    public List<Task> getAvailableTasks() {
        return getAll().stream()
                .filter(task -> task.getVolunteer() == null || task.getStatus() == status.AVAILABLE)
                .collect(Collectors.toList());
    }

    public List<Task> getCompletedTasks() {
        return getAll().stream()
                .filter(task -> task.getStatus() == status.COMPLETED)
                .collect(Collectors.toList());
    }

    public List<Task> getAssignedTasks(int volunteerId) {
        return getAll().stream()
                .filter(task -> task.getVolunteer() != null && task.getVolunteer().getUserId() == volunteerId)
                .collect(Collectors.toList());
    }
}
