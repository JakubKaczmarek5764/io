package db;

import Resources.VolunteerEvaluation;

import java.util.List;

public class VolunteerEvaluationRepository implements IRepository<VolunteerEvaluation> {

    @Override
    public void add(VolunteerEvaluation entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(VolunteerEvaluation.class, id);
    }

    @Override
    public void update(VolunteerEvaluation entity) {
        Repository.update(entity);
    }

    @Override
    public VolunteerEvaluation get(long id) {
        return Repository.get(VolunteerEvaluation.class, id);
    }

    @Override
    public List<VolunteerEvaluation> getAll() {
        return Repository.getAll(VolunteerEvaluation.class);
    }

}
