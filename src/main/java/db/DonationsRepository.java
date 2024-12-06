package db;

import Classes.Donation;

import java.util.List;

public class DonationsRepository implements IRepository<Donation> {

    @Override
    public void add(Donation entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Donation.class, id);
    }

    @Override
    public void update(Donation entity) {
        Repository.update(entity);
    }

    @Override
    public Donation get(long id) {
        return Repository.get(Donation.class, id);
    }

    @Override
    public List<Donation> getAll() {
        return Repository.getAll(Donation.class);
    }
}
