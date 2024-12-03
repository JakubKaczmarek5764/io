package io.skph;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    private Long chatId;
    private String Name;
    private boolean isArchive;

    public Chat(Long chatId, String name, boolean isArchive) {
        this.chatId = chatId;
        Name = name;
        this.isArchive = isArchive;
    }

    public void addMessage(Message message) {
        messages.add(message);
        ///todo Tutaj dodać logikę wysyłania powiadomień lub zapisywania wiadomości w bazie danych
    }

    public void addParticipant(User user) {
        users.add(user);
    }

    public void removeParticipant(User user) {
        users.remove(user);
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }
}
