package Chat;

import Classes.Report;
import Classes.User;
import db.ChatRepository;
import db.MessageRepository;
import db.ReportRepository;
import db.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    @Autowired
    private ChatFactory chatClientFactory;

    @Autowired
    private ChatServer chatServer;

    private Map<Integer, ChatClient> chatClients;

    private ChatRepository chatRepository = new ChatRepository();
    private MessageRepository messageRepository = new MessageRepository();
    private UsersRepository usersRepository = new UsersRepository();

    @PostConstruct
    public void init() {
        ChatRepository chatRepository = new ChatRepository();
        int numOfChats = chatRepository.getAll().size();
        if (numOfChats == 0) {
            Chat globalChat = new Chat("Global Chat", false);
            chatRepository.add(globalChat);
        }

        //System.out.println("Service: " + this);
        chatClients = new ConcurrentHashMap<>();

        for (User user : usersRepository.getAll()) {
            chatClients.put(user.getUserId(), this.createNewChatSession(user));
            System.out.println("Added chat client in chat service for user: " + user.getUserId());
            if (!chatClients.get(user.getUserId()).isInChat(1L)) {
                try {
                    chatClients.get(user.getUserId()).joinNewChat(chatRepository.get(1L));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void tryAddNewChatSession(int userId) {
        try {
            if(chatClients.containsKey(userId)) {
                return;
            }

            UsersRepository usersRepository = new UsersRepository();
            User user = usersRepository.get(userId);
            if (user == null) {
                throw new BadRequestException("User not found");
            }
            ChatClient chatClient = this.createNewChatSession(user);
            if (chatClient != null) {
                this.chatClients.put(userId, chatClient);

                ChatRepository chatRepository = new ChatRepository();
                chatClient.joinNewChat(chatRepository.get(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChatClient createNewChatSession(User user) {
        try {
            return chatClientFactory.createChatClient(user);
        } catch (Exception e) {
            System.out.println("Error starting new chat: " + e.getMessage());
        }
        return null;
    }

    public ChatServer createNewChatServer() {
        try {
            return chatClientFactory.createChatServer();
        } catch (Exception e) {
            System.out.println("Error starting new chat server: " + e.getMessage());
        }
        return null;
    }

    public String sendMessage(String message, Integer senderId, long chatId) throws IOException {
        ChatClient client = chatClients.get(senderId);

        if (client == null) {
            System.out.println("ChatClient for senderId " + senderId + " is null. Available keys: " + chatClients.keySet());
            throw new BadRequestException("No ChatClient found for senderId: " + senderId);
        }

        client.sendMessage(chatId, message);
        return "Message sent";
    }

    public String createChat(int userId, String name) {
        try {
            ChatClient client = chatClients.get(userId);

            Chat chat = new Chat(name, false);
            client.createNewChat(chat);

            return "Chat created";
        } catch (Exception e) {
            System.out.println("Error starting new chat: " + e.getMessage());
            return "Error starting new chat: " + e.getMessage();
        }
    }

    public String addToChat(int adminId, Long chatId, int userId) {
        try {
            ChatClient adminClient = chatClients.get(adminId);
            if (!adminClient.isInChat(chatId)) {
                return "You are not in the chat";
            }

            ChatClient client = chatClients.get(userId);
            if (client == null) {
                return "Given user does not exist";
            } else {
                client.joinNewChat(chatRepository.get(chatId));
                return "Added to chat";
            }
        } catch (Exception e) {
            System.out.println("Error starting new chat: " + e.getMessage());
            return "Error starting new chat: " + e.getMessage();
        }
    }

    public String changeChatStatus(Long chatId, int userId) {
        try {
            Chat chat = chatRepository.get(chatId);
            ChatClient client = chatClients.get(userId);
            if (client == null) {
                return "Given user does not exist";
            }
            if (!client.isInChat(chatId)) {
                return "You are not in the chat";
            }

            if (chat.isArchive()) {
                chat.setArchive(false);
                chatRepository.update(chat);
                return "Enabled chat";
            } else {
                chat.setArchive(true);
                chatRepository.update(chat);
                return "Archived chat";
            }

        } catch (Exception e) {
            System.out.println("Error changing chat status: " + e.getMessage());
            return "Error changing chat status: " + e.getMessage();
        }
    }

    public List<ChatRestController.UserDTO> getUsers(Long chatId) {
        try {
            List<ChatRestController.UserDTO> users = new ArrayList<>();

            List<User> usersRepo = usersRepository.getAll();

            for (User user : usersRepo) {
                if (!chatClients.get(user.getUserId()).isInChat(chatId)) {
                    users.add(new ChatRestController.UserDTO(user.getUserId(), user.getFirstName() + " " + user.getLastName()));
                }
            }

            return users;
        } catch (Exception e) {
            System.out.println("Error fetching users: " + e.getMessage());
            return null;
        }
    }

    public List<ChatRestController.UserChatsDto> getUserChats(int userId) {
        try {
            List<Chat> chats = chatRepository.getAll();
            ChatClient client = chatClients.get(userId);
            List<ChatRestController.UserChatsDto> userChats = new ArrayList<>();
            for (Chat chat : chats) {
                System.out.println(chat.getName());
                if (client.isInChat(chat.getChatId())) {
                    String content;
                    try {
                        Message message = chat.getMessages().getLast();
                        content = message.getContent();
                    } catch (NoSuchElementException e) {
                        content = "";
                    }

                    userChats.add(new ChatRestController.UserChatsDto(chat.getChatId(), chat.getName(), content));
                }
            }

            return userChats;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching user chats: " + e.getMessage());
            return null;
        }
    }

    public List<ChatRestController.ChatHistoryDto> getChatHistory(Long chatId, int userId) {
        try {
            ChatClient client = chatClients.get(userId);
            if (client == null) {
                return null;
            }

            Chat chat = chatRepository.get(chatId);
            if (client.isInChat(chat.getChatId())) {
                List<Message> messages = messageRepository.getByChatId(chatId);
                //Hibernate.initialize(messages);

                List<ChatRestController.ChatHistoryDto> chatHistory = new ArrayList<>();
                for (Message message : messages) {
                    chatHistory.add(new ChatRestController.ChatHistoryDto(message.getSender().getFirstName() + " " + message.getSender().getLastName(), message.getContent(), message.getTimestamp()));
                }
                return chatHistory;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error fetching chat history: " + e.getMessage());
            return null;
        }
    }

    public List<ChatRestController.ChatHistoryDto> getOldNotifications(int userId) {
        try {
            MessageRepository messageRepository = new MessageRepository();
            List<Message> oldMessages = messageRepository.getOldMessagesByUserId(userId);

            List<ChatRestController.ChatHistoryDto> oldNotifications = new ArrayList<>();
            for (Message message : oldMessages) {
                oldNotifications.add(new ChatRestController.ChatHistoryDto(message.getSender().getFirstName() + " " + message.getSender().getLastName(), message.getContent(), message.getTimestamp()));
            }
            return oldNotifications;
        } catch (Exception e) {
            System.out.println("Error fetching old notifications: " + e.getMessage());
            return null;
        }
    }

    public String createChatForReport(int volunteerId, int victimId, int reportId) {
        try {
            UsersRepository usersRepository = new UsersRepository();
            ReportRepository reportRepository = new ReportRepository();
            ChatRepository chatRepository = new ChatRepository();
            User user = usersRepository.get(volunteerId);
            User user2 = usersRepository.get(victimId);
            Report report = reportRepository.get(reportId);
            Chat newChat = new Chat(user2.getFirstName() + " " + user2.getLastName() + " - " + reportId, false);
            chatRepository.add(newChat);

            ChatClient client = chatClients.get(volunteerId);
            ChatClient client2 = chatClients.get(victimId);
            if (client == null || client2 == null) {
                throw new RuntimeException( "Given user does not exist");
            }
            client.joinNewChat(newChat);
            client2.joinNewChat(newChat);

            client2.sendMessage(newChat.getChatId(), "Submition date: " + report.getCompletion_date() + " Victim: " + user.getFirstName() + " " + user.getLastName() + ".");
            client.sendMessage(newChat.getChatId(), "Volunteer: " + user.getFirstName() + " " + user.getLastName() + " was appended to this report.");


            return "Chat created";
        } catch (Exception e) {
            System.out.println("Error creating chat: " + e.getMessage());
            throw new RuntimeException("Error creating chat: " + e.getMessage());
        }
    }

    public List<ChatRestController.UserDTO> getChatUsers(Long chatId) {
        try {
            ChatRepository chatRepository = new ChatRepository();
            Chat chat = chatRepository.get(chatId);

            if (chat == null) {
                throw new RuntimeException( "Given chat does not exist");
            }

            List<ChatRestController.UserDTO> users = new ArrayList<>();
            for (User user : chat.getUsers()) {
                users.add(new ChatRestController.UserDTO(user.getUserId(), user.getFirstName() + " " + user.getLastName()));
            }

            return users;
        } catch (Exception e) {
            System.out.println("Error fetching users: " + e.getMessage());
            throw new RuntimeException("Error fetching users: " + e.getMessage());
        }
    }
}
