package skph;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable {
    public Notification(String message, int recipientId, String chatName, LocalDateTime date) {
        this.message = message;
        this.recipientId = recipientId;
        this.chatName = chatName;
        this.date = date;
    }

    private static final long serialVersionUID = 1L;
    private String message;
    private int recipientId;
    private String chatName;
    private LocalDateTime date;


    public String notifyUser() {
        return ("skph.Notification for you: " + message);
    }

    @Override
    public String toString() {
        return "skph.Notification{" +
                "message='" + message + '\'' +
                ", recipientId=" + recipientId +
                ", chatName='" + chatName + '\'' +
                ", date=" + date +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public String getChatName() {
        return chatName;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
