package db;
import Classes.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import Chat.Chat;

import java.util.ArrayList;
import java.util.List;


public class UsersRepository implements IRepository<User> {


    @Override
    public void add(User entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(User.class, id);
    }

    @Override
    public void update(User entity) {
        Repository.update(entity);
    }

    @Override
    public User get(long id) {
        return Repository.get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return Repository.getAll(User.class);
    }

    public List<Long> getUserChats(int userId) {
        List<Long> chats = new ArrayList<>();
        User user = get(userId);

        for (Chat chat : user.getChats()) {
            chats.add(chat.getChatId());
        }

        return chats;
    }
}
