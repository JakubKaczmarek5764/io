package Chat;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import Classes.User;
import db.ChatRepository;
import db.UsersRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatClient {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Map<Long, Chat> activeChats = new HashMap<>(); // Mapowanie chatId -> skph.Chat
    private User user;
    private ChatWebSocketController chatWebSocketController;
    private ChatRepository chatRepository = new ChatRepository();
    private UsersRepository usersRepository = new UsersRepository();

    public boolean isInChat(long chatId) {
        return activeChats.containsKey(chatId);
    }

    public void createConnection(String serverAddress, User user, ChatWebSocketController chatWebSocketController) throws Exception {
        this.user = user;
        // Nawiązywanie połączenia z serwerem
        Socket socket = new Socket(serverAddress, 12345);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        this.chatWebSocketController = chatWebSocketController;

        // Uruchomienie wątku odbierającego wiadomości
        new ReaderThread().start();

        for(Long chat : usersRepository.getUserChats(user.getUserId())) {
            this.joinChat(chatRepository.get(chat));
        }
    }

    public void joinNewChat(Chat chat) throws Exception {
        activeChats.put(chat.getChatId(), chat);
        chatRepository.addUserToChat(chat.getChatId(), user.getUserId());

        Map<String, Object> joinRequest = new HashMap<>();
        joinRequest.put("chatId", chat.getChatId());
        joinRequest.put("user", user);

        out.writeObject(joinRequest); // Informacja dla serwera, że klient dołącza do nowego chatu
        out.flush();

        System.out.println("New chat joined: " + chat.getChatId());
    }

    public void createNewChat(Chat chat) throws Exception {
        chatRepository.createNewChat(chat);
        //chatRepository.addUserToChat(chat.getChatId(), user.getUserId());
        //activeChats.put(chat.getChatId(), chat);
        this.joinNewChat(chat);

        System.out.println("New chat created: " + chat.getChatId());
    }

    /**
     * Rejestruje chat w kliencie.
     */
    public void joinChat(Chat chat) throws IOException {
        activeChats.put(chat.getChatId(), chat);

        Map<String, Object> joinRequest = new HashMap<>();
        joinRequest.put("chatId", chat.getChatId());
        joinRequest.put("user", user);

        out.writeObject(joinRequest); // Informacja dla serwera, że klient dołącza do tego chatu
        out.flush();

        System.out.println("Chat joined: " + chat.getChatId());
    }

    /**
     * Wysyła wiadomość do określonego chatu.
     */
    public void sendMessage(Long chatId, String content) throws IOException {
        Chat chat = activeChats.get(chatId);
        if (chat == null) {
            System.out.println("Nie nalezysz do tego chatu: " + chatId);
            throw new BadRequestException("You are not a part of the chat");
        }
        if (chat.isArchive()) {
            System.out.println("Chat is archived");
            throw new NotActiveException("Chat is archived");
        }
        // Tworzenie wiadomości
        Message message = new Message(content, user, chat, LocalDateTime.now());
        out.writeObject(message); // Wysyłanie wiadomości do serwera
        out.flush();
    }

    /**
     * Wątek do odbierania wiadomości z serwera.
     */
    private class ReaderThread extends Thread {
        public void run() {
            try {
                Object messageObject;
                while ((messageObject = in.readObject()) != null) {
                    if (messageObject instanceof Notification) {
                        Notification notification = (Notification) messageObject;
                        System.out.println(notification.notifyUser());
                        chatWebSocketController.sendToUser(user.getUserStringId(), notification);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Blad podczas odbierania wiadomosci: " + e);
            }
        }
    }
}
