package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRestController {
    @Autowired
    private ChatRestService chatRestService;


    /**
     * Handles HTTP POST requests to send a message in a chat.
     *
     * @param messageDTO The {@link MessageDTO} object containing the message, sender ID, and chat ID.
     * @return A {@link String} indicating the result of the send operation.
     *
     * Example usage:
     * <pre>
     * {@code
     * POST /chat/sendMessage HTTP/1.1
     * Host: server
     * Content-Type: application/json
     *
     * {
     *   "message": "Hello everyone",
     *   "senderId": 123,
     *   "chatId": 456
     * }
     * }
     * </pre>
     */
    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody MessageDTO messageDTO) {
        try {
            chatRestService.sendMessage(messageDTO.getMessage(), messageDTO.getSenderId(), messageDTO.getChatId());
        } catch (Exception e) {
            e.printStackTrace();
            return "Couldn't send message";
        }

        return "Message sent";
    }

    @PostMapping("/createChat")
    public String createChat(@RequestBody ChatDTO chatDTO) {
        return chatRestService.createChat(chatDTO.getUserId(), chatDTO.getName());
    }

    @PostMapping("/addToChat")
    public String addToChat(@RequestBody AddToChatDTO addToChatDTO) {
        return chatRestService.addToChat(addToChatDTO.getAdminId(), addToChatDTO.getChatId(), addToChatDTO.getUserId());
    }

    @PutMapping("/archiveChat")
    public String changeChatStatus(@RequestBody UserIdChatIdDTO changeChatStatusDTO) {
        return chatRestService.changeChatStatus(changeChatStatusDTO.getChatId(), changeChatStatusDTO.getUserId());
    }

    @GetMapping("/getUserChats")
    public List<UserChatsDto> getUserChats(@RequestBody UserIdDto userId) {
        return chatRestService.getUserChats(userId.getUserId());
    }

    @GetMapping("/getUsers")
    public List<UserDTO> getUsers(@RequestBody ChatIdDto chatId) {
        return chatRestService.getUsers(chatId.getChatId());
    }

    @GetMapping("/getChatHistory")
    public List<ChatHistoryDto> getChatHistory(@RequestBody UserIdChatIdDTO userIdChatIdDTO) {
        return chatRestService.getChatHistory(userIdChatIdDTO.getChatId(), userIdChatIdDTO.getUserId());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    public static class UserIdDto {
        private int userId;

        public void setUserId(int userId) { this.userId = userId; }
        public int getUserId() { return userId; }
    }

    public static class ChatIdDto {
        private Long chatId;

        public void setChatId(Long chatId) { this.chatId = chatId; }
        public Long getChatId() { return chatId; }
    }

    public static class UserChatsDto {
        private Long chatId;
        private String name;
        private String lastMessage;

        public Long getChatId() { return chatId; }
        public String getName() { return name; }
        public String getLastMessage() { return lastMessage; }

        public UserChatsDto(Long chatId, String name, String lastMessage) {
            this.chatId = chatId;
            this.name = name;
            this.lastMessage = lastMessage;
        }
    }

    public static class UserDTO {
        private int userId;
        private String name;

        public void setUserId(int userId) { this.userId = userId; }
        public void setName(String name) { this.name = name; }

        public UserDTO(int userId, String name) { this.userId = userId; this.name = name; }
    }

    public static class ChatDTO {
        private int userId;
        private String name;

        public int getUserId() { return userId; }
        public String getName() { return name; }
    }

    public static class AddToChatDTO {
        private int adminId;
        private Long chatId;
        private int userId;

        public int getAdminId() { return adminId; }
        public Long getChatId() { return chatId; }
        public int getUserId() { return userId; }
    }

    public static class UserIdChatIdDTO {
        private Long chatId;
        private int userId;

        public Long getChatId() { return chatId; }
        public int getUserId() { return userId; }
    }

    public static class ChatHistoryDto {
        private String senderName;
        private String message;
        private LocalDateTime timestamp;

        public String getSenderName() { return senderName; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }

        public ChatHistoryDto(String senderName, String message, LocalDateTime timestamp) {
            this.senderName = senderName;
            this.message = message;
            this.timestamp = timestamp;
        }
    }

    /**
     * Data Transfer Object (DTO) for sending a message.
     */
    public static class MessageDTO {
        private String message;
        private Integer senderId;
        private long chatId;

        public String getMessage() { return message; }
        public Integer getSenderId() { return senderId; }
        public long getChatId() { return chatId; }
    }
}
