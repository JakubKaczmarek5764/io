package db;

import Chat.Chat;
import Classes.User;

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

    public void addUserToChat(long chatId, long userId) {
        Chat chat = Repository.get(Chat.class, chatId);
        User user = Repository.get(User.class, userId);
        if (!chat.getUsers().contains(user)) {
            chat.addUser(user);
            Repository.update(chat);
        }
    }

    public void createNewChat(Chat chat) {
        if(chat.getChatId() == null) Repository.add(chat);
    }
}
