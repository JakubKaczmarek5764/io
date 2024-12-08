package Chat;

import java.io.*;
import java.net.*;
import java.util.*;
import Classes.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class ChatServer {
    // Mapowanie chatId na obiekt skph.Chat oraz zestaw strumieni klientów
    private static Map<Long, Chat> chats = new HashMap<>();
    private static Map<Long, Set<ObjectOutputStream>> chatClientWriters = new HashMap<>();

    // Metoda inicjalizująca czaty
    public static void initializeChats() { ///todo zrobić pobieranie czatów z bazy
        Chat chat1 = new Chat(1L, "skph.Chat One", false);
        Chat chat2 = new Chat(2L, "skph.Chat Two", false);

        chats.put(chat1.getChatId(), chat1);
        chats.put(chat2.getChatId(), chat2);

        chatClientWriters.put(chat1.getChatId(), new HashSet<>());
        chatClientWriters.put(chat2.getChatId(), new HashSet<>());

        System.out.println("Initialized chats: " + chats.keySet());
    }

    @PostConstruct
    public void start() throws Exception {
        initializeChats();
        new Thread(() -> {
            try(ServerSocket serverSocket = new ServerSocket(12345)) {
                System.out.println("Chat server is running...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                // Oczekiwanie na chatId od klienta
                Map<String, Object> joinRequest = (Map<String, Object>) in.readObject();
                Long chatId = (Long) joinRequest.get("chatId");
                User user = (User) joinRequest.get("user");

                Chat chat = chats.get(chatId);

                if (chat == null) {
                    System.out.println("Invalid chat ID: " + chatId);
                    socket.close();
                    return;
                }

                // Dodanie klienta do czatu
                chat.addParticipant(user);
                chatClientWriters.get(chatId).add(out);

                System.out.println("skph.User added to chat: " + chatId);

                Object messageObject;
                while ((messageObject = in.readObject()) != null) {
                    if (messageObject instanceof Notification) {
                        Notification notification = (Notification) messageObject;
                        sendToChat(notification, chatId, out);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try { socket.close(); } catch (IOException e) {}
                if (out != null) {
                    chatClientWriters.values().forEach(writers -> writers.remove(out));
                }
            }
        }
    }

    private static void sendToChat(Notification notification, Long chatId, ObjectOutputStream sender) {
        Set<ObjectOutputStream> writers = chatClientWriters.get(chatId);
        if (writers != null) {
            for (ObjectOutputStream writer : writers) {
                if (writer != sender) { // Pomijanie nadawcy
                    try {
                        writer.writeObject(notification);
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("Error sending notification: " + e);
                    }
                }
            }
        }
    }
}
