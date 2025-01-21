package db;

import Chat.Message;
import Chat.Chat;
import Classes.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Message> getByChatId(Long id) {
        EntityManager entityManager = Repository.getEntityManagerFactory().createEntityManager();
        Chat chat = Repository.get(Chat.class, id);
        String jpql = "SELECT m FROM Message m WHERE m.chat = :chat";
        TypedQuery<Message> query = entityManager.createQuery(jpql, Message.class);
        query.setParameter("chat", chat);

        List<Message> list = query.getResultList();
        return list;
    }

    public List<Message> getOldMessagesByUserId(int id) {
        EntityManager entityManager = Repository.getEntityManagerFactory().createEntityManager();
        User user = Repository.get(User.class, id);
        LocalDateTime lastActivityTime = user.getLastActivityTime();
        if (lastActivityTime == null) {
            lastActivityTime = LocalDateTime.now();
        }

        List<Message> list = new ArrayList<>();

        for (Chat chat : user.getChats()) {
            System.out.println("Chat id: " + chat.getChatId());
            String jpql = "SELECT m FROM Message m WHERE m.chat = :chat AND m.timestamp >= :lastActivityTime";
            TypedQuery<Message> query = entityManager.createQuery(jpql, Message.class);
            query.setParameter("chat", chat);
            query.setParameter("lastActivityTime", lastActivityTime);
            list.addAll(query.getResultList());
        }

        System.out.println("Old messages list");
        System.out.println("Size: " + list.size());
        System.out.println("lastActivityTime: " + lastActivityTime);

        for (Message m : list) {
            System.out.println(m.getContent());
        }
        return list;
    }
}
