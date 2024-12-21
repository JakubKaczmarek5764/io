package Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRestController {
    private ChatRestService chatRestService = new ChatRestService();

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
