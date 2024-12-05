package db;
import java.util.List;

public interface CRUDManager<T> {
    void add(T entity);
    void remove(Class<T> entityClass, long id);
    void update(T entity);
    T get(Class<T> entityClass, long id);
    List<T> getAll(Class<T> entityClass);
}
