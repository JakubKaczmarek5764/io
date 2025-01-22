package db;

import Classes.Victim;

import java.util.List;

public class VictimRepository implements IRepository<Victim> {
    @Override
    public void add(Victim entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Victim.class, id);
    }

    @Override
    public void update(Victim entity) {
        Repository.update(entity);
    }

    @Override
    public Victim get(long id) {
        return Repository.get(Victim.class, id);
    }

    @Override
    public List<Victim> getAll() {
        return Repository.getAll(Victim.class);
    }
}
