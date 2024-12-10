package Security;

import Classes.User;
import db.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserService(UsersRepository usersRepository, JwtUtils jwtUtils) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtils = jwtUtils;
    }

    public boolean register(RegisterRequest request) {
        String loginHash = hash(request.getLogin());
        Optional<User> existingUser = usersRepository.findByLoginHash(loginHash);

        if (existingUser.isPresent()) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getNickName(),
                request.getFirstName(),
                request.getLastName(),
                loginHash,
                hashedPassword,
                request.getEmail(),
                request.getPhoneNumber(),
                LocalDate.now(),
                LocalDate.now()
        );

        usersRepository.add(user);
        return true;
    }

    public String login(LoginRequest request) {
        String loginHash = hash(request.getLogin());
        Optional<User> user = usersRepository.findByLoginHash(loginHash);

        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPasswordHash())) {
            User loggedInUser = user.get();
            loggedInUser.setLastLogin(LocalDate.now());
            usersRepository.update(loggedInUser);
            return jwtUtils.generateToken(loggedInUser);
        }

        throw new IllegalArgumentException("Niepoprawny login lub hasło.");
    }

    private String hash(String input) {
        return String.valueOf(input.hashCode());
    }
}
