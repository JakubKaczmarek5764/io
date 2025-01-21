package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO messageDTO) {
        try {
            return chatRestService.sendMessage(messageDTO.getMessage(), messageDTO.getSenderId(), messageDTO.getChatId());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Couldn't send message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     Handles HTTP POST requests to create a new chat.
     @param chatDTO The {@link ChatRestController.ChatDTO} object containing the user ID of the creator and the name of the chat.
     @return A {@link String} indicating the result of the chat creation operation.

     Example usage:
     {@code
         POST /chat/createChat HTTP/1.1
         Host: server
         Content-Type: application/json 
         {
            "userId": 123,
            "name": "Study Group"
         }
     }
     */
    @PostMapping("/createChat")
    public ResponseEntity<String> createChat(@RequestBody ChatDTO chatDTO) {
        return chatRestService.createChat(chatDTO.getUserId(), chatDTO.getName());
    }

    /**
     Handles HTTP POST requests to add a user to a chat.
     @param addToChatDTO The {@link ChatRestController.AddToChatDTO} object containing the admin ID, chat ID, and user ID to be added.
     @return A {@link String} indicating the result of the add operation.

     Example usage:
     {@code
        POST /chat/addToChat HTTP/1.1
        Host: server
        Content-Type: application/json
        {
            "adminId": 1,
            "chatId": 456,
            "userId": 789
        }
     }
     */
    @PostMapping("/addToChat")
    public ResponseEntity<String> addToChat(@RequestBody AddToChatDTO addToChatDTO) {
        return chatRestService.addToChat(addToChatDTO.getAdminId(), addToChatDTO.getChatId(), addToChatDTO.getUserId());
    }

    /**
     Handles HTTP PUT requests to archive a chat.
     @param changeChatStatusDTO The {@link ChatRestController.UserIdChatIdDTO} object containing the chat ID and user ID initiating the change.
     @return A {@link String} indicating the result of the operation.

     Example usage:
     {@code
         PUT /chat/archiveChat HTTP/1.1
         Host: server
         Content-Type: application/json 
         {
            "chatId": 123,
            "userId": 456
         }
     }
     */
    @PutMapping("/changeChatStatus")
    public ResponseEntity<String> changeChatStatus(@RequestBody UserIdChatIdDTO changeChatStatusDTO) {
        return chatRestService.changeChatStatus(changeChatStatusDTO.getChatId(), changeChatStatusDTO.getUserId());
    }

    /**
     Handles HTTP GET requests to retrieve the list of chats a user is part of.
     @param userId The {@link ChatRestController.UserIdDto} object containing the user ID.
     @return A list of {@link ChatRestController.UserChatsDto} objects representing the user's chats.

     Example usage:
     {@code
         GET /chat/getUserChats HTTP/1.1 
         Host: server
         Content-Type: application/json
         {
            "userId": 123
         }
     }
     */
    @GetMapping("/getUserChats")
    public ResponseEntity<List<UserChatsDto>> getUserChats(@RequestBody UserIdDto userId) {
        return chatRestService.getUserChats(userId.getUserId());
    }

    /**
     Handles HTTP GET requests to retrieve the list of users in a chat.
     @param chatId The {@link ChatRestController.ChatIdDto} object containing the chat ID.
     @return A list of {@link ChatRestController.UserDTO} objects representing the users that you can to the chat.

     Example usage:
     {@code
         GET /chat/getUsers HTTP/1.1
         Host: server
         Content-Type: application/json
         {
            "chatId": 456
         }
     }
     */
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestBody ChatIdDto chatId) {
        return chatRestService.getUsers(chatId.getChatId());
    }

    /**
     Handles HTTP GET requests to retrieve the chat history for a specific chat.
     @param chatId The long type variable containing the chat ID.
     @param userId The int type variable containing the user ID.
     @return A list of {@link ChatRestController.ChatHistoryDto} objects representing the chat history.

     Example usage:
     {@code
         GET /chat/getChatHistory HTTP/1.1
         Host: server
         Content-Type: application/json
         {
            "chatId": 456,
            "userId": 123
         }
     }
     */
    @GetMapping("/getChatHistory")
    public ResponseEntity<List<ChatHistoryDto>> getChatHistory(@RequestParam Long chatId, @RequestParam int userId) {
        return chatRestService.getChatHistory(chatId, userId);
    }

    /**
     Handles HTTP GET requests to retrieve old notifications for a user.
     @param userIdDto The {@link ChatRestController.UserIdDto} object containing the user ID.
     @return A list of {@link ChatRestController.ChatHistoryDto} objects representing old notifications.

     Example usage:
     {@code
         GET /chat/getOldNotifications HTTP/1.1
         Host: server
         Content-Type: application/json
         {
            "userId": 123
         }
     }
     */
    @GetMapping("/getOldNotifications")
    public ResponseEntity<List<ChatHistoryDto>> getOldNotifications(@RequestBody UserIdDto userIdDto) {
        return chatRestService.getOldNotifications(userIdDto.getUserId());
    }

    @PostMapping("/createChatForReport")
    public ResponseEntity<String> createChatForReport(@RequestBody ChatForReport chatForReport) {
        return chatRestService.createChatForReport(chatForReport.getVolunteerId(), chatForReport.getVictimId(), chatForReport.getReportId());
    }

    @GetMapping("/getChatUsers")
    public ResponseEntity<List<UserDTO>> getChatUsers(@RequestBody ChatIdDto chatIdDto) {
        return chatRestService.getChatUsers(chatIdDto.getChatId());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    public static class ChatForReport {
        private int volunteerId;
        private int victimId;
        private int reportId;

        public int getVolunteerId() { return volunteerId; }
        public int getVictimId() { return victimId; }
        public int getReportId() { return reportId; }
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
