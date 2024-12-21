package Chat;

import Classes.User;
import db.ChatRepository;
import db.MessageRepository;
import db.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    @Autowired
    private ChatFactory chatClientFactory;

    @Autowired
    private ChatServer chatServer;

    private Map<Integer, ChatClient> chatClients;

    private ChatRepository chatRepository = new ChatRepository();
    private MessageRepository messageRepository = new MessageRepository();
    private UsersRepository usersRepository = new UsersRepository();

    @PostConstruct
    public void init() {
        chatClients = new ConcurrentHashMap<>();

        for (User user : usersRepository.getAll()) {
            chatClients.put(user.getUserId(), this.createNewChatSession(user));
            System.out.println("Added chat client in chat service for user: " + user.getUserId());
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

    public void sendMessage(String message, Integer senderId, long chatId) throws IOException {
        ChatClient client = chatClients.get(senderId);

        if (client == null) {
            System.out.println("ChatClient for senderId " + senderId + " is null. Available keys: " + chatClients.keySet());
            throw new IllegalStateException("No ChatClient found for senderId: " + senderId);
        }

        client.sendMessage(chatId, message);
    }
}
