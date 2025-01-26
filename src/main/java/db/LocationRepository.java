package db;

import Classes.Location;

import java.util.List;


public class LocationRepository implements IRepository<Location> {
    @Override
    public void add(Location entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Location.class, id);
    }

    @Override
    public void update(Location entity) {
        Repository.update(entity);
    }

    @Override
    public Location get(long id) {
        return Repository.get(Location.class, id);
    }

    @Override
    public List<Location> getAll() {
        return Repository.getAll(Location.class);
    }
}
