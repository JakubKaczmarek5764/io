package db;

import java.util.List;

public interface IRepository<T> {

    void add(T entity);
    void remove(long id);
    void update(T entity);
    T get(long id);
    List<T> getAll();
}
