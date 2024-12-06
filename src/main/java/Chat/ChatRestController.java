package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatRestController {
    private final ChatRestService chatRestService;

    @Autowired
    public ChatRestController(ChatRestService chatRestService) {
        this.chatRestService = chatRestService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
