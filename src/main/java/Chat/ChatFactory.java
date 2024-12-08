package Chat;

import Classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatFactory {

    @Autowired
    private ChatWebSocketController chatWebSocketController;

    public ChatClient createChatClient(User user) throws Exception {
        ChatClient chatClient = new ChatClient();
        chatClient.createConnection("localhost", user, chatWebSocketController);
        return chatClient;
    }

    public ChatServer createChatServer() throws Exception {
        return new ChatServer();
    }
}
