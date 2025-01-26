package Security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController implements ISecurity {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            boolean flag = userService.register(request);
            if (!flag){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"status\":\"error\"}");
            }
            return ResponseEntity.ok("{\"status\":\"success\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"status\":\"error\"}");
        }
    }

    @Override
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

