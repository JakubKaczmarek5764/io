package Chat;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConnectionService {
    public void saveConnectionClosedTime(String userId, LocalDateTime closeTime) {
        System.out.println("Saving connection closed time for user: " + userId + " at " + closeTime);
    }

    public void saveConnectionEstablishedTime(String userId, LocalDateTime estTime) {
        System.out.println("Saving connection established time for user: " + userId + " at " + estTime);
    }


}
