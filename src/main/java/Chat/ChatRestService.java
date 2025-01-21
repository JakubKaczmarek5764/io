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

    public ResponseEntity<String> createChat(int userId, String name) {
        try {
            return new ResponseEntity<>(chatService.createChat(userId, name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addToChat(int adminId, Long chatId, int userId) {
        try {
            return new ResponseEntity<>(chatService.addToChat(adminId, chatId, userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> changeChatStatus(Long chatId, int userId) {
        try {
            return new ResponseEntity<>(chatService.changeChatStatus(chatId, userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ChatRestController.UserDTO>> getUsers(Long chatId) {
        try {
            return new ResponseEntity<>(chatService.getUsers(chatId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ChatRestController.UserChatsDto>> getUserChats(int userId) {
        try {
            return new ResponseEntity<>(chatService.getUserChats(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ChatRestController.ChatHistoryDto>> getChatHistory(Long chatId, int userId) {
        try {
            return new ResponseEntity<>(chatService.getChatHistory(chatId, userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ChatRestController.ChatHistoryDto>> getOldNotifications(int userId) {
        try {
            return new ResponseEntity<>(chatService.getOldNotifications(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> createChatForReport(int volunteerId, int victimId, int reportId) {
        try {
            return new ResponseEntity<>(chatService.createChatForReport(volunteerId, victimId, reportId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ChatRestController.UserDTO>> getChatUsers(Long chatId) {
        try {
            return new ResponseEntity<>(chatService.getChatUsers(chatId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
