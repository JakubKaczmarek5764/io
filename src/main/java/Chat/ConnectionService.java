package Chat;

import Classes.User;
import db.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConnectionService {
    private static final UsersRepository usersRepository = new UsersRepository();

    public void saveConnectionClosedTime(String userId, LocalDateTime closeTime) {
        System.out.println("Saving connection closed time for user: " + userId + " at " + closeTime);
        User user = usersRepository.get(Long.parseLong(userId));
        user.setLastActivityTime(closeTime);
        usersRepository.update(user);
    }

    public void saveConnectionEstablishedTime(String userId, LocalDateTime estTime) {
        System.out.println("Saving connection established time for user: " + userId + " at " + estTime);
    }


}
