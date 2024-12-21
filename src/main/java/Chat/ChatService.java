package Chat;

import Classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatFactory chatClientFactory;

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
