package SKPH;

import Chat.*;
import Chat.ChatClient;
import Chat.ChatServer;
import Classes.Volunteer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"Chat"})
public class SkphApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SkphApplication.class, args);

        ///to get receive from server e.g. http://localhost:8080/chat/hello

        System.out.println("SKHP Server Started");
    }

    @Bean
    public CommandLineRunner run(ChatController chatController) {
        return args -> {
            Thread.sleep(30000);

            ChatServer server = chatController.createNewChatServer();

            Chat chat = new Chat(1L, "Chat" + 1, false);

            Volunteer user = new Volunteer("userFName", "userLName");
            user.setUserId(1);
            ChatClient client = chatController.createNewChatSession(user);
            client.joinChat(chat);

            Volunteer user1 = new Volunteer("userFName1", "userLName1");
            user1.setUserId(2);
            ChatClient client1 = chatController.createNewChatSession(user1);
            client1.joinChat(chat);

            client1.sendMessage(1L, "Papaj 2137");
        };
    }
}
