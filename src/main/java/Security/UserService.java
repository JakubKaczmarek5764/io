//package Security;
//
//import Classes.*;
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
//
//        Optional<User> existingUser = usersRepository.findByEmail(request.getEmail());
//
//        if (existingUser.isPresent()) {
//            return false;
//        }
//
//        String hashedPassword = passwordEncoder.encode(request.getPassword());
//
//        switch(request.getRole()) {
//            case "victim":
//                Victim victim = new Victim(request.getFirstName(), request.getSurname(), null);
//                victim.setPasswordHash(hashedPassword);
//                victim.setEmail(request.getEmail());
//                victim.setPhoneNumber(request.getPhone());
//                victim.setRegistrationDate(LocalDate.now());
//                new UsersRepository().add(victim);
//                break;
//            case "volunteer":
//                Volunteer volunteer = new Volunteer(request.getFirstName(), request.getSurname(), request.getPassword(),
//                        request.getEmail(), request.getPhone(), LocalDate.now());
//                volunteer.setLastActivityTime(LocalDateTime.now());
//                volunteer.setPasswordHash(hashedPassword);
//                volunteer.setResource(new Resource(volunteer));
//                new UsersRepository().add(volunteer);
//                break;
//            case "donator":
//                Donator donator = new Donator(request.getFirstName(), request.getSurname(), null);
//                donator.setLastActivityTime(LocalDateTime.now());
//                donator.setPasswordHash(hashedPassword);
//                donator.setEmail(request.getEmail());
//                donator.setPhoneNumber(request.getPhone());
//                donator.setRegistrationDate(LocalDate.now());
//                new UsersRepository().add(donator);
//                break;
//            default:
//                return false;
//        }
//        return true;
//    }
//
//    public LoginResponse login(LoginRequest request) {
//        Optional<User> user = usersRepository.findByEmail(request.getEmail());
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
