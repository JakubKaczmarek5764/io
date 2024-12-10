package db;

import Chat.Message;

import java.util.List;

public class MessageRepository implements IRepository<Message> {
    @Override
    public void add(Message entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Message.class, id);
    }

    @Override
    public void update(Message entity) {
        Repository.update(entity);
    }

    @Override
    public Message get(long id) {
        return Repository.get(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        return Repository.getAll(Message.class);
    }
}
