package Chat;

import java.io.*;
import java.net.*;
import java.util.*;
import Classes.User;
import db.ChatRepository;
import db.MessageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class ChatServer {
    // Mapowanie chatId na obiekt Chat oraz zestaw strumieni klientów
    private static Map<Long, Chat> chats = new HashMap<>();
    private static Map<Long, Set<ObjectOutputStream>> chatClientWriters = new HashMap<>();
    private static ChatRepository chatRepository = new ChatRepository();

    // Metoda inicjalizująca czaty
    public static void initializeChats() {
        List<Chat> fetchedChats = chatRepository.getAll();

        for (Chat chat : fetchedChats) {
            chats.put(chat.getChatId(), chat);
            chatClientWriters.put(chat.getChatId(), new HashSet<>());
        }

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
        private MessageRepository messageRepository = new MessageRepository();

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
                    ChatRepository chatRepository = new ChatRepository();
                    Chat chat1 = chatRepository.get(chatId);
                    if (chat1 == null) {
                        System.out.println("Invalid chat ID: " + chatId);
                        socket.close();
                        return;
                    }
                    chats.put(chatId, chat1);
                    chatClientWriters.put(chatId, new HashSet<>());
                    chat1.addParticipant(user);
                    System.out.println("New chat added in server: " + chatId);
                } else {
                    // Dodanie klienta do czatu
                    chat.addParticipant(user);
                }

                if (chatClientWriters.isEmpty() || !chatClientWriters.get(chatId).contains(out)) {
                    chatClientWriters.get(chatId).add(out);
                    System.out.println("Server added User to chat: " + chatId);
                }

                Object messageObject;
                while ((messageObject = in.readObject()) != null) {
                    if (messageObject instanceof Message message) {
                        try {
                            messageRepository.add(message);
                            message.getChat().addMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("Cannot send the message");
                        }
                        sendToChat(new Notification(message.getContent(), message.getSender().getFirstName() + " " + message.getSender().getLastName(), message.getChat().getName(), message.getTimestamp()), message.getChat().getChatId(), out);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e);
                }
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
