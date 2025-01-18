package Chat;

import db.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.NotActiveException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRestService {
    @Autowired
    private ChatService chatService;

    public ResponseEntity<String> sendMessage(String message, Integer senderId, long chatId) throws IOException {
        try {
            return new ResponseEntity<>(chatService.sendMessage(message, senderId, chatId), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotActiveException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    public List<ChatRestController.ChatHistoryDto> getOldNotifications(int userId) {
        return chatService.getOldNotifications(userId);
    }

}
