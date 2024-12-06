package Chat;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ChatClient {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Map<Long, Chat> activeChats = new HashMap<>(); // Mapowanie chatId -> skph.Chat

    public ChatClient(String serverAddress) throws Exception {
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
        out.writeObject(chat.getChatId()); // Informacja dla serwera, że klient dołącza do tego chatu
        out.flush();
    }

    /**
     * Wysyła wiadomość do określonego chatu.
     */
    public void sendMessage(Long chatId, String content, User sender) throws IOException {
        Chat chat = activeChats.get(chatId);
        if (chat == null) {
            System.out.println("Nie nalezysz do tego chatu: " + chatId);
            return;
        }
        // Tworzenie wiadomości
        Message message = new Message(content, sender.getId(), chatId, LocalDateTime.now());
        Notification notification = new Notification(message.getContent(), sender.getId(), chat.getName(), LocalDateTime.now());
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

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient("localhost");

        // Przykładowe czaty
        Chat chat1 = new Chat(1L, "Chat1", false);
        Chat chat2 = new Chat(2L, "Chat2", false);

        // Przykładowy użytkownik
        User user = new User(1, "Alice");

        // Dołączanie do czatów
        client.joinChat(chat1);
        client.joinChat(chat2);

        // Wysyłanie wiadomości
        client.sendMessage(1L, "Hello to Chat1!", user);
        client.sendMessage(2L, "Hi Chat2!", user);
    }
}
