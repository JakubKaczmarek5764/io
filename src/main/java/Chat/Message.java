package Chat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private int messageId;
    private int senderId;
    private String content;
    private LocalDateTime timestamp;
    private long chatId;


    public Message(String content, int senderId, long chatId, LocalDateTime timestamp) {
        this.content = content;
        this.senderId = senderId;
        this.chatId = chatId;
        this.timestamp = timestamp;
    }

    public String editMessage(String content) {
        this.content = content;
        return "Edited";
    }

    public int getSender() {
        return senderId;
    }

    public String getContent() {
        return content;
    }
}
