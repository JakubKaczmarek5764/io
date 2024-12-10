package db;

import java.util.List;

public class MessageRepository implements IRepository<MessageRepository> {
    @Override
    public void add(MessageRepository entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(MessageRepository.class, id);
    }

    @Override
    public void update(MessageRepository entity) {
        Repository.update(entity);
    }

    @Override
    public MessageRepository get(long id) {
        return Repository.get(MessageRepository.class, id);
    }

    @Override
    public List<MessageRepository> getAll() {
        return Repository.getAll(MessageRepository.class);
    }
}
