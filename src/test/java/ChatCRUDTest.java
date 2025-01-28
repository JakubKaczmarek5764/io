import Chat.Chat;
import db.ChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class ChatCRUDTest {

    private ChatRepository repo;
    private Chat chat;


    @BeforeEach
    public void setUp() {
        chat = new Chat("czacik", false);
        repo = new ChatRepository();

    }
    @AfterEach
    void cleanUp() {
       repo.getAll().forEach(chat -> repo.remove(chat.getChatId()));

    }

    @Test
    public void addTest() {
        repo.add(chat);
        Chat returnedChat = repo.get(chat.getChatId());

        Assertions.assertEquals(chat.getChatId(), returnedChat.getChatId());
        Assertions.assertEquals(chat.getName(), returnedChat.getName());

    }

    @Test
    public void removeTest() {
        repo.add(chat);
        repo.remove(chat.getChatId());
        Chat returnedChat = repo.get(chat.getChatId());
        Assertions.assertNull(returnedChat);
    }

    @Test
    public void updateTest() {
        repo.add(chat);
        chat.setName("czacik2");
        repo.update(chat);
        Chat returnedChat = repo.get(chat.getChatId());
        Assertions.assertEquals(chat.getChatId(), returnedChat.getChatId());
        Assertions.assertEquals(chat.getName(), returnedChat.getName());
    }

    @Test
    public void getAllTest() {
        repo.add(chat);
        Chat chat2 = new Chat("czacik2", false);
        repo.add(chat2);
        Assertions.assertTrue(repo.getAll().size() > 0);
    }

}
