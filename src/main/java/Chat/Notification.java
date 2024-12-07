package Chat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable {
    public Notification(String message, String senderName, String chatName, LocalDateTime date) {
        this.message = message;
        this.senderName = senderName;
        this.chatName = chatName;
        this.date = date;
    }

    private static final long serialVersionUID = 1L;
    private String message;
    private String senderName;
    private String chatName;
    private LocalDateTime date;


    public String notifyUser() {
        return ("Notification for you from " + senderName + ": " + message);
    }

    @Override
    public String toString() {
        return "skph.Notification{" +
                "message='" + message + '\'' +
                ", senderName=" + senderName +
                ", chatName='" + chatName + '\'' +
                ", date=" + date +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getChatName() {
        return chatName;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
