package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Classes.User;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    public ChatClient createNewChatSession(User user) {
        try {
            return chatService.startNewChat(user);
        } catch (Exception e) {
            System.out.println("Error starting new chat: " + e.getMessage());
        }
        return null;
    }

    public ChatServer createNewChatServer() {
        try {
            return chatService.startNewChatServer();
        } catch (Exception e) {
            System.out.println("Error starting new chat server: " + e.getMessage());
        }
        return null;
    }
}

