package db;

import Classes.Charity;

import java.util.List;

public class CharityRepository implements IRepository<Charity> {
    @Override
    public void add(Charity entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Charity.class, id);
    }

    @Override
    public void update(Charity entity) {
        Repository.update(entity);
    }

    @Override
    public Charity get(long id) {
        return Repository.get(Charity.class, id);
    }

    @Override
    public List<Charity> getAll() {
        return Repository.getAll(Charity.class);
    }
}
