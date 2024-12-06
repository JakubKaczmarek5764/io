package Chat;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import Classes.User;

public class ChatClient {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Map<Long, Chat> activeChats = new HashMap<>(); // Mapowanie chatId -> skph.Chat
    private User user;

    public ChatClient(String serverAddress, User user) throws Exception {
        this.user = user;
        // Nawiązywanie połączenia z serwerem
        Socket socket = new Socket(serverAddress, 12345);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        // Uruchomienie wątku odbierającego wiadomości
        new ReaderThread().start();
    }

    /**
     * Rejestruje nowy chat w kliencie.
     */
    public void joinChat(Chat chat) throws IOException {
        activeChats.put(chat.getChatId(), chat);

        Map<String, Object> joinRequest = new HashMap<>();
        joinRequest.put("chatId", chat.getChatId());
        joinRequest.put("user", user);

        out.writeObject(joinRequest); // Informacja dla serwera, że klient dołącza do tego chatu
        out.flush();
    }

    /**
     * Wysyła wiadomość do określonego chatu.
     */
    public void sendMessage(Long chatId, String content) throws IOException {
        Chat chat = activeChats.get(chatId);
        if (chat == null) {
            System.out.println("Nie nalezysz do tego chatu: " + chatId);
            return;
        }
        // Tworzenie wiadomości
        Message message = new Message(content, user.getUser_id(), chatId, LocalDateTime.now());
        Notification notification = new Notification(message.getContent(), user.getUser_id(), chat.getName(), LocalDateTime.now());
        out.writeObject(notification); // Wysyłanie wiadomości do serwera
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
                        System.out.println("Otrzymano wiadomosc: " + notification.notifyUser());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Blad podczas odbierania wiadomosci: " + e);
            }
        }
    }
}