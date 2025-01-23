//package Security;
//
//import Classes.User;
//import db.UsersRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserServiceIntegrationTest {
//
//    private UserService userService;
//
//    private UsersRepository usersRepository;
//
//    private JwtUtils jwtUtils;
//
//    private JwtConfig jwtConfig;
//
//    @BeforeEach
//    void setUp() {
//        usersRepository = new UsersRepository();
//        jwtConfig = new JwtConfig();
//        jwtUtils = new JwtUtils(jwtConfig);
//        userService = new UserService(usersRepository, jwtUtils);
//
//        User user1 = new User(
//                "TestUser1",
//                "John",
//                "Doe",
//                "hashedLogin1",
//                "hashedPassword1",
//                "john.doe@example.com",
//                "123456789",
//                LocalDate.now(),
//                LocalDate.now()
//        );
//
//        // Tworzenie drugiego użytkownika
//        User user2 = new User(
//                "TestUser2",
//                "Jane",
//                "Smith",
//                "hashedLogin2",
//                "hashedPassword2",
//                "jane.smith@example.com",
//                "987654321",
//                LocalDate.now(),
//                LocalDate.now()
//        );
//
//        // Dodawanie użytkowników do bazy
//        usersRepository.add(user1);
//        usersRepository.add(user2);
//    }
//
//    @AfterEach
//    void tearDown() {
//        usersRepository.getAll().forEach(user -> usersRepository.remove(user.getUserId()));
//    }
//
//    @Test
//    void testRegisterAndLogin() {
//
//        // Rejestracja użytkownika
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setFirstName("Test");
//        registerRequest.setSurname("User");
//        registerRequest.setPassword("testPassword");
//        registerRequest.setEmail("test@example.com");
//        registerRequest.setPhone("123456789");
//
//        boolean registerSuccess = userService.register(registerRequest);
//        assertTrue(registerSuccess);
//
//        // Logowanie użytkownika
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setLogin("testLogin");
//        loginRequest.setPassword("testPassword");
//
//        String jwtToken = userService.login(loginRequest);
//        assertNotNull(jwtToken);
//        assertTrue(jwtToken.startsWith("eyJ")); // Sprawdzamy, czy token JWT został wygenerowany
//    }
//
//    @Test
//    void testRegisterDuplicateLogin() {
//        // Rejestracja użytkownika
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setNickName("Tester");
//        registerRequest.setFirstName("Test");
//        registerRequest.setLastName("User");
//        registerRequest.setLogin("duplicateLogin");
//        registerRequest.setPassword("testPassword");
//        registerRequest.setEmail("test@example.com");
//        registerRequest.setPhoneNumber("123456789");
//
//        userService.register(registerRequest);
//
//        // Próba rejestracji tego samego loginu
//        boolean registerSuccess = userService.register(registerRequest);
//        assertFalse(registerSuccess);
//    }
//
//    @Test
//    void testLoginInvalidPassword() {
//        // Rejestracja użytkownika
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setFirstName("Test");
//        registerRequest.setSurname("User");
//        registerRequest.setPassword("testPassword");
//        registerRequest.setEmail("test@example.com");
//        registerRequest.setPhone("123456789");
//
//        userService.register(registerRequest);
//
//        // Logowanie z błędnym hasłem
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setLogin("testLogin");
//        loginRequest.setPassword("wrongPassword");
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.login(loginRequest));
//        assertEquals("Niepoprawny login lub hasło.", exception.getMessage());
//    }
//}
