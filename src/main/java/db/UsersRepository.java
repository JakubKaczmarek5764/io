package db;
import Classes.User;
import jakarta.persistence.EntityManager;
import Chat.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    public Optional<User> findByEmail(String email) {
        try (EntityManager em = Repository.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst();
        }
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
