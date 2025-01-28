package Security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ISecurity {

    @PostMapping("/signup")
    ResponseEntity<String> register(@RequestBody RegisterRequest request);

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);
}
