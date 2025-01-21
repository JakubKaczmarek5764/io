package SKPH;

import Chat.*;
import Chat.ChatClient;
import Chat.ChatServer;
import Classes.User;
import Classes.Volunteer;
import db.ChatRepository;
import db.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"Chat", "Classes"})
public class SkphApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SkphApplication.class, args);

        ///to get receive from server e.g. http://localhost:8080/chat/hello

        System.out.println("SKHP Server Started");
    }

    //@Bean
    public CommandLineRunner run(ChatService chatService) {
        return args -> {
            UsersRepository usersRepository = new UsersRepository();
            ChatRepository chatRepository = new ChatRepository();

            //Thread.sleep(30000);

            ChatServer server = chatService.createNewChatServer();

            Chat chat = chatRepository.get(1L);

            //Volunteer user = new Volunteer("userFName", "userLName");
            //usersRepository.add(user);

            //Volunteer user1 = new Volunteer("userFName1", "userLName1");
            //usersRepository.add(user1);

            User user = usersRepository.get(1L);
            User user1 = usersRepository.get(2L);

            System.out.println(chat);
            System.out.println(user);
            System.out.println(user1);

            ChatClient client = chatService.createNewChatSession(user);
            //client.joinNewChat(chat);

            ChatClient client1 = chatService.createNewChatSession(user1);
            //client1.joinNewChat(chat);

            client1.sendMessage(1L, "Papaj 2137");
        };
    }
}
