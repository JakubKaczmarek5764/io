package db;

import Classes.Report;
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

    //todo: METODA DO LOGIKI..

    public void assignResource(int reportId, Resource resource, int quantity) {
        Report report = Repository.get(Report.class, reportId);
        if (report == null) {
            throw new IllegalArgumentException("Report with ID " + reportId + " does not exist.");
        }
        if (resource.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient quantity in resource. Available: " + resource.getQuantity() + ", Requested: " + quantity);
        }

        resource.updateQuantity(-quantity);

        report.addResource(resource);
        Repository.update(resource);
        Repository.update(report);
    }
}
