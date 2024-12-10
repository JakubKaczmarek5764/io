package db;

import Classes.Authority;

import java.util.List;

public class AuthoritiesRepository implements IRepository<Authority> {
    @Override
    public void add(Authority entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Authority.class, id);
    }

    @Override
    public void update(Authority entity) {
        Repository.update(entity);
    }

    @Override
    public Authority get(long id) {
        return Repository.get(Authority.class, id);
    }

    @Override
    public List<Authority> getAll() {
        return Repository.getAll(Authority.class);
    }
}
