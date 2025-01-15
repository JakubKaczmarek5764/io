package Chat;

import db.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRestService {
    @Autowired
    private ChatService chatService;

    public void sendMessage(String message, Integer senderId, long chatId) throws IOException {
        chatService.sendMessage(message, senderId, chatId);
    }

    public String createChat(int userId, String name) {
        return chatService.createChat(userId, name);
    }

    public String addToChat(int adminId, Long chatId, int userId) {
        return chatService.addToChat(adminId, chatId, userId);
    }

    public String changeChatStatus(Long chatId, int userId) {
        return chatService.changeChatStatus(chatId, userId);
    }

    public List<ChatRestController.UserDTO> getUsers(Long chatId) {
        return chatService.getUsers(chatId);
    }

    public List<ChatRestController.UserChatsDto> getUserChats(int userId) {
        return chatService.getUserChats(userId);
    }

    public List<ChatRestController.ChatHistoryDto> getChatHistory(Long chatId, int userId) {
        return chatService.getChatHistory(chatId, userId);
    }
}
