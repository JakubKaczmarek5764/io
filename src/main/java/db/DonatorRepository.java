package db;

import Classes.Donator;

import java.util.List;

public class DonatorRepository implements IRepository<Donator> {
    @Override
    public void add(Donator entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Donator.class, id);
    }

    @Override
    public void update(Donator entity) {
        Repository.update(entity);
    }

    @Override
    public Donator get(long id) {
        return Repository.get(Donator.class, id);
    }

    @Override
    public List<Donator> getAll() {
        return Repository.getAll(Donator.class);
    }
}

