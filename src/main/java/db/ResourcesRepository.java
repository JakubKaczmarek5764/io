package db;

import Resources.Resource;
import Classes.resourceType;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Resource> getVolunteers() {
        return getAll().stream()
                .filter(resource -> resource.getType() == resourceType.VOLUNTEER)
                .collect(Collectors.toList());
    }
}
