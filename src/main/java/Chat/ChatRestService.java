package Chat;

import db.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatRestService {
    private final UsersRepository usersRepository = new UsersRepository();
    //private final ChatsRepository chatsRepository = new ChatsRepository();
}
