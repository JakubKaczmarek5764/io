package Chat;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatRestController {
    private final ChatRestService chatRestService = new ChatRestService();

}
