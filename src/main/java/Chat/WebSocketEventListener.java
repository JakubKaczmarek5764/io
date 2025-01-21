package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {
    @Autowired
    private ConnectionService connectionService;

    private final ConcurrentHashMap<String, String> sessionIdToUserIdMap = new ConcurrentHashMap<>();

    @Autowired
    ChatService chatService;

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Object simpConnectMessage = event.getMessage().getHeaders().get("simpConnectMessage");

        String userId = null;
        String sessionId = headerAccessor.getSessionId();

        if (simpConnectMessage instanceof org.springframework.messaging.Message) {
            org.springframework.messaging.Message<?> connectMessage = (org.springframework.messaging.Message<?>) simpConnectMessage;
            StompHeaderAccessor connectHeaderAccessor = StompHeaderAccessor.wrap(connectMessage);
            userId = connectHeaderAccessor.getFirstNativeHeader("userId");
        }

        if (userId != null) {
            synchronized (userId.intern()) {
                //System.out.println("Service: " + chatService);
                if (!sessionIdToUserIdMap.containsValue(userId)) {
                    chatService.tryAddNewChatSession(Integer.parseInt(userId));
                    sessionIdToUserIdMap.put(sessionId, userId);
                }
            }
        }

//        System.out.println("Native Headers: " + headerAccessor.toNativeHeaderMap());
//        System.out.println("Session Attributes: " + headerAccessor.getSessionAttributes());
//        System.out.println("Message Headers: " + event.getMessage().getHeaders());
//        System.out.println("UserId: " + userId);

        LocalDateTime connectedTime = LocalDateTime.now();
        connectionService.saveConnectionEstablishedTime(userId, connectedTime);
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();

        String userId = sessionIdToUserIdMap.remove(sessionId);

//        System.out.println("Native Headers: " + headerAccessor.toNativeHeaderMap());
//        System.out.println("Session Attributes: " + headerAccessor.getSessionAttributes());
//        System.out.println("Message Headers: " + event.getMessage().getHeaders());
//        System.out.println("UserId: " + userId);

        if (userId != null) {
            synchronized (userId.intern()) {
                LocalDateTime disconnectedTime = LocalDateTime.now();
                connectionService.saveConnectionClosedTime(userId, disconnectedTime);
            }
        }


    }
}
