//package Security;
//
//import Classes.Donator;
//import Classes.User;
//import Classes.Victim;
//import Classes.Volunteer;
//import db.UsersRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class UserService {
//    private final UsersRepository usersRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//    private final JwtUtils jwtUtils;
//
//    public UserService(UsersRepository usersRepository, JwtUtils jwtUtils) {
//        this.usersRepository = usersRepository;
//        this.passwordEncoder = new BCryptPasswordEncoder();
//        this.jwtUtils = jwtUtils;
//    }
//
//    public boolean register(RegisterRequest request) {
//        String loginHash = hash(request.getEmail());
//        Optional<User> existingUser = usersRepository.findByLoginHash(loginHash);
//
//        if (existingUser.isPresent()) {
//            return false;
//        }
//
//        String hashedPassword = passwordEncoder.encode(request.getPassword());
//
//        switch(request.getRole()) {
//            case "victim":
//                Victim victim = new Victim(request.getFirstName(), request.getSurname(), loginHash, hashedPassword, request.getEmail(), request.getPhone(), LocalDate.now(), LocalDateTime.now(), null);
//                new UsersRepository().add(victim);
//                break;
//            case "volunteer":
//                Volunteer volunteer = new Volunteer(request.getFirstName(), request.getSurname(), loginHash, hashedPassword, request.getEmail(), request.getPhone(), LocalDate.now(), LocalDateTime.now());
//                new UsersRepository().add(volunteer);
//                break;
//            case "donator":
//                Donator donator = new Donator(request.getFirstName(), request.getSurname(), loginHash, hashedPassword, request.getEmail(), request.getPhone(), LocalDate.now(), LocalDateTime.now(), null);
//                new UsersRepository().add(donator);
//                break;
//            default:
//                return false;
//        }
//        return true;
//    }
//
//    public LoginResponse login(LoginRequest request) {
//        String loginHash = hash(request.getLogin());
//        Optional<User> user = usersRepository.findByLoginHash(loginHash);
//
//        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPasswordHash())) {
//            User loggedInUser = user.get();
//            loggedInUser.setLastActivityTime(LocalDateTime.now());
//            usersRepository.update(loggedInUser);
//            return new LoginResponse(Integer.toString(loggedInUser.getUserId()), jwtUtils.generateToken(loggedInUser), "success");
//        }
//
//
//        throw new IllegalArgumentException("Niepoprawny login lub hasło.");
//    }
//
//    private String hash(String input) {
//        return String.valueOf(input.hashCode());
//    }
//}
