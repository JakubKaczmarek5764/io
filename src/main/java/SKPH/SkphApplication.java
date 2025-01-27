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

import java.time.LocalDate;

@SpringBootApplication
@ComponentScan(basePackages = {"Chat", "Classes","FileGeneration", "Security", "db"})
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

            Thread.sleep(10000);

            Volunteer user = new Volunteer("userFName", "userLName", "hgfhfg", "lol@wp.pl", "123456789", LocalDate.now());
            usersRepository.add(user);

            Volunteer user1 = new Volunteer("userFName1", "userLName1", "hgfhfg", "lol2@wp.pl", "123456788", LocalDate.now());
            usersRepository.add(user1);

            System.out.println(user);
            System.out.println(user1);
        };
    }
}
