package Chat;

import Classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatFactory chatClientFactory;

    public ChatClient startNewChat(User user) throws Exception {
        return chatClientFactory.createChatClient(user);
    }

    public ChatServer startNewChatServer() throws Exception {
        return chatClientFactory.createChatServer();
    }
}
