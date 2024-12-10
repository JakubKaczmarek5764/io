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

        };
    }
}
