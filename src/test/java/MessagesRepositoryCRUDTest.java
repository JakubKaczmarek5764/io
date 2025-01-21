
import Chat.Chat;
import Chat.Message;
import Classes.Charity;
import Classes.User;
import Classes.Victim;
import Classes.Volunteer;
import db.CharityRepository;
import db.ChatRepository;
import db.MessageRepository;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesRepositoryCRUDTest {

    private MessageRepository messRepo;
    private ChatRepository chatRepo;
    private UsersRepository usersRepo;

    private Message message;

    private Chat chat;

    private User user;

    @BeforeEach
    public void setUp() {

        user = new Volunteer("John", "Doe");
        chat = new Chat("czacik", false);
        messRepo = new MessageRepository();
        chatRepo = new ChatRepository();
        usersRepo = new UsersRepository();

    }

    @AfterEach
    void cleanUp() {
        messRepo.getAll().forEach(message -> messRepo.remove(message.getMessageId()));
        usersRepo.getAll().forEach(user -> usersRepo.remove(user.getUserId()));
        chatRepo.getAll().forEach(chat -> chatRepo.remove(chat.getChatId()));


    }

    @Test
    public void testAdd() {
        chatRepo.add(chat);
        usersRepo.add(user);
        message = new Message("Hello world", user, chat, LocalDateTime.now());
        messRepo.add(message);
        Message returnedMessage = messRepo.get(message.getMessageId());

        Assertions.assertEquals(message.getMessageId(), returnedMessage.getMessageId());
        Assertions.assertEquals(message.getContent(), returnedMessage.getContent());

    }

    @Test
    public void testUpdate() {
        chatRepo.add(chat);
        usersRepo.add(user);
        message = new Message("Hello world", user, chat, LocalDateTime.now());
        messRepo.add(message);
        Message returnedMessage = messRepo.get(message.getMessageId());
        returnedMessage.setContent("Hello world 2");
        messRepo.update(returnedMessage);
        Message updatedMessage = messRepo.get(returnedMessage.getMessageId());
        Assertions.assertTrue(updatedMessage.getContent().equals("Hello world 2"));
        Assertions.assertFalse(updatedMessage.getContent().equals(message.getContent()));
    }

    @Test
    public void testGetAll() {
        chatRepo.add(chat);
        usersRepo.add(user);
        message = new Message("Hello world", user, chat, LocalDateTime.now());
        messRepo.add(message);
        Message message2 = new Message("Hello world 2", user, chat, LocalDateTime.now());
        messRepo.add(message2);
        Assertions.assertEquals(2, messRepo.getAll().size());
    }

}
