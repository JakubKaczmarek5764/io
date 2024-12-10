package db;

import Classes.Task;

import java.util.List;

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
}
