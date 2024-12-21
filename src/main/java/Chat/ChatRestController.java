package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
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
