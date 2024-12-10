package db;

import Chat.Chat;

import java.util.List;

public class ChatRepository implements IRepository<Chat> {
    @Override
    public void add(Chat entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Chat.class, id);
    }

    @Override
    public Chat get(long id) {
        return Repository.get(Chat.class, id);
    }

    @Override
    public List<Chat> getAll() {
        return Repository.getAll(Chat.class);
    }

    @Override
    public void update(Chat entity) {
        Repository.update(entity);
    }
}
