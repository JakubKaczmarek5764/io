//import java.util.Base64;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.PBEKeySpec;
//import java.security.SecureRandom;
//
//public class AuthManager {
//    private static final int SALT_LENGTH = 16;
//    private static final int HASH_LENGTH = 64;
//    private static final int ITERATIONS = 10000;
//    private static final byte[] LOGIN_GLOBAL_SALT = "global_salt_for_logins".getBytes();
//
//    private final DatabaseHandler dbHandler;
//
//    public AuthManager() {
//        dbHandler = new DatabaseHandler();
//    }
//
//    public boolean login(String username, String password) {
//        try {
//            String hashedUsername = hashLogin(username);
//            UserRecord userRecord = dbHandler.getUserRecord(hashedUsername);
//            if (userRecord == null) {
//                return false;
//            }
//
//            String hashedPassword = hashPassword(password, Base64.getDecoder().decode(userRecord.getSalt()));
//            return hashedPassword.equals(userRecord.getHashedPassword());
//        } catch (Exception e) {
//            System.err.println("Błąd podczas logowania: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean register(String username, String password) {
//        try {
//            String hashedUsername = hashLogin(username);
//            if (dbHandler.getUserRecord(hashedUsername) != null) {
//                return false; // Użytkownik istnieje
//            }
//
//            byte[] salt = generateSalt();
//            String hashedPassword = hashPassword(password, salt);
//
//            return dbHandler.addUserRecord(hashedUsername, Base64.getEncoder().encodeToString(salt), hashedPassword);
//        } catch (Exception e) {
//            System.err.println("Błąd podczas rejestracji: " + e.getMessage());
//            return false;
//        }
//    }
//
//    private String hashLogin(String username) {
//        try {
//            PBEKeySpec spec = new PBEKeySpec(username.toCharArray(), LOGIN_GLOBAL_SALT, ITERATIONS, HASH_LENGTH * 8);
//            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//            byte[] hash = factory.generateSecret(spec).getEncoded();
//            return Base64.getEncoder().encodeToString(hash);
//        } catch (Exception e) {
//            throw new RuntimeException("Błąd podczas hashowania loginu", e);
//        }
//    }
//
//    private String hashPassword(String password, byte[] salt) {
//        try {
//            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH * 8);
//            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//            byte[] hash = factory.generateSecret(spec).getEncoded();
//            return Base64.getEncoder().encodeToString(hash);
//        } catch (Exception e) {
//            throw new RuntimeException("Błąd podczas hashowania hasła", e);
//        }
//    }
//
//    private byte[] generateSalt() {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[SALT_LENGTH];
//        random.nextBytes(salt);
//        return salt;
//    }
//}
