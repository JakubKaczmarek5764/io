package Security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        boolean success = userService.register(request);
        if (success) {
            return ResponseEntity.ok("Rejestracja zakończona sukcesem.");
        }
        return ResponseEntity.badRequest().body("Rejestracja nie powiodła się. Login może być zajęty.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String token = userService.login(request);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

