package db;

import Classes.Resource;

import java.util.List;

public class ResourcesRepository implements IRepository<Resource> {

    @Override
    public void add(Resource entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Resource.class, id);
    }

    @Override
    public void update(Resource entity) {
        Repository.update(entity);
    }

    @Override
    public Resource get(long id) {
        return Repository.get(Resource.class, id);
    }

    @Override
    public List<Resource> getAll() {
        return Repository.getAll(Resource.class);
    }
}
