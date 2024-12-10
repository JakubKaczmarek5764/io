
import Chat.Chat;
import Chat.Message;
import Classes.Charity;
import Classes.Victim;
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

    private CharityRepository charityRepo;
    private Message message;

    private Chat chat;

    private Victim victim;
    private Charity charity;

    @BeforeEach
    public void setUp() {
        charity = new Charity("Red Cross", "Helping people in need");
        victim = new Victim("John", "Doe", charity);
        chat = new Chat("czacik", false);
        message = new Message("Hello world", victim, chat, LocalDateTime.now());
        messRepo = new MessageRepository();
        chatRepo = new ChatRepository();
        usersRepo = new UsersRepository();
        charityRepo = new CharityRepository();

    }

//    @AfterEach
//    void cleanUp() {
//        messRepo.getAll().forEach(message -> messRepo.remove(message.getMessageId()));
//        chatRepo.getAll().forEach(chat -> chatRepo.remove(chat.getChatId()));
//        usersRepo.getAll().forEach(user -> usersRepo.remove(user.getUserId()));
//    }

    @Test
    public void testAdd() {
        charityRepo.add(charity);
        chatRepo.add(chat);
        usersRepo.add(victim);

//        messRepo.add(message);
//        Message returnedMessage = messRepo.get(message.getMessageId());
//
//        Assertions.assertEquals(message.getMessageId(), returnedMessage.getMessageId());
//        Assertions.assertEquals(message.getContent(), returnedMessage.getContent());

    }

    @Test
    public void testUpdate() {
        messRepo.add(message);
        Message returnedMessage = messRepo.get(message.getMessageId());
        returnedMessage.editMessage("Hello world, I'm here");
        messRepo.update(returnedMessage);
        Message updatedMessage = messRepo.get(returnedMessage.getMessageId());
        Assertions.assertTrue(updatedMessage.getContent().equals("Hello world, I'm here"));
        Assertions.assertFalse(updatedMessage.getContent().equals(message.getContent()));
    }

    @Test
    public void testRemove() {
        messRepo.add(message);
        messRepo.remove(message.getMessageId());
        Message returnedMessage = messRepo.get(message.getMessageId());
        Assertions.assertNull(returnedMessage);
    }

    @Test
    public void testGetAll() {
        messRepo.add(message);
        Message message2 = new Message("Hello world", null, null, LocalDateTime.now());
        messRepo.add(message2);

        assertTrue(messRepo.getAll().size() > 0);
        assertTrue(messRepo.getAll().contains(message));
    }

}
