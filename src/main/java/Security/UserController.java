package Security;

import db.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        boolean success = userService.register(request);
        if (success) {
            return ResponseEntity.ok("{\"status\":\"success\"}");
        }
        return ResponseEntity.badRequest().body("{\"status\":\"error\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok((userService.login(request)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, "error"));
        }
    }
}

