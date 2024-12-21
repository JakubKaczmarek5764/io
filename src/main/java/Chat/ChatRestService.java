package Chat;

import db.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatRestService {
    @Autowired
    private ChatService chatService;

    public void sendMessage(String message, Integer senderId, long chatId) throws IOException {
        chatService.sendMessage(message, senderId, chatId);
    }
}
