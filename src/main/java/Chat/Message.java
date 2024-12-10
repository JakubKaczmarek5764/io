package Chat;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import Classes.User;

@Entity
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User sender;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "chatId", nullable = false)
    private Chat chat;



    public Message(String content, User sender, Chat chat, LocalDateTime timestamp) {
        this.content = content;
        this.sender = sender;
        this.chat = chat;
        this.timestamp = timestamp;
    }

    public Message() {

    }

    public int getMessageId() {
        return messageId;
    }

    public String editMessage(String content) {
        this.content = content;
        return "Edited";
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}
