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

    public static class MessageDTO {
        private String message;
        private Integer senderId;
        private long chatId;

        public String getMessage() { return message; }
        public Integer getSenderId() { return senderId; }
        public long getChatId() { return chatId; }
    }
}
