package Chat;

import Classes.User;
import db.ChatRepository;
import db.MessageRepository;
import db.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatFactory chatClientFactory;

    private ChatRepository chatRepository = new ChatRepository();
    private MessageRepository messageRepository = new MessageRepository();
    private UsersRepository usersRepository = new UsersRepository();

    List<ChatClient> chatClients = new ArrayList<>();
    ChatServer chatServer = null;

    @PostConstruct
    public void init() {
        chatServer = new ChatServer();
        for (User user : usersRepository.getAll()) {
            chatClients.add(this.createNewChatSession(user));
            System.out.println("Added chat client in chat service.");
        }
    }

    public ChatClient createNewChatSession(User user) {
        try {
            return chatClientFactory.createChatClient(user);
        } catch (Exception e) {
            System.out.println("Error starting new chat: " + e.getMessage());
        }
        return null;
    }

    public ChatServer createNewChatServer() {
        try {
            return chatClientFactory.createChatServer();
        } catch (Exception e) {
            System.out.println("Error starting new chat server: " + e.getMessage());
        }
        return null;
    }
}
