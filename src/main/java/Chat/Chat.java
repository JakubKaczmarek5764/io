package Chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Classes.User;
import db.MessageRepository;
import jakarta.persistence.*;

@Entity
public class Chat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isArchive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "chatId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    public Chat(Long chatId, String name, boolean isArchive) {
        this.chatId = chatId;
        this.name = name;
        this.isArchive = isArchive;
    }
    public Chat(String name, boolean isArchive) {
        this.name = name;
        this.isArchive = isArchive;
    }

    public Chat() {
        
    }

    public void addMessage(Message message) {
        //messages.add(message);
        MessageRepository messageRepository = new MessageRepository();
        messageRepository.add(message);
    }

    public void addParticipant(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public void removeParticipant(User user) {
        if (users.contains(user)) {
            users.remove(user);
        }
    }

    public List<Message> getChatHistory() {
        return messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

//    @Override
//    public String toString() {
//        return "Chat{" +
//                "chatId=" + chatId +
//                ", name='" + name + '\'' +
//                ", isArchive=" + isArchive +
//                ", users=" + users +
//                ", messages=" + messages +
//                '}';
//    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
