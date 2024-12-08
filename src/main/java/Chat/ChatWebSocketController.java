package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 5000) public void sendPeriodicMessages() {
        Notification notification = new Notification("Test notification", "System", "Test Chat", LocalDateTime.now());
        template.convertAndSendToUser("1","/queue/notifications", notification);
    }

    public void sendToUser(String userId, Notification notification) {
        template.convertAndSendToUser(userId, "/queue/notifications", notification);
        System.out.println(notification.getMessage());
        System.out.println(userId);
    }
}
