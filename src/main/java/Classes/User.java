package Classes;

import jakarta.persistence.*;

import Chat.Chat;
import Chat.Message;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
@Table(name = "users")
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Chat> chats;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Message> messages;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String loginHash;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    public User(String firstName, String lastName, String loginHash, String passwordHash, String email, String phoneNumber, LocalDate registrationDate, LocalDateTime lastLogin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginHash = loginHash;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserStringId() {
        return userId + "";
    }

    public void setUserId(int userId) { //tylko na potrzeby testów chatu, potem usunąć
        this.userId = userId;
    }


    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getLastActivityTime() {
        return lastLogin;
    }

    public void setLastActivityTime(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    //loginHash oraz data rejstracji są niezmienne
    public String getLoginHash() {
        return loginHash;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}
