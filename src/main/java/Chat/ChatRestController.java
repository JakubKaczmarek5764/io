package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRestController {
    private final ChatRestService chatRestService;
    //private ChatServer chatServer;
    //private List<ChatClient> clients = new ArrayList<>();

    @Autowired
    public ChatRestController(ChatRestService chatRestService) {
        this.chatRestService = chatRestService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
