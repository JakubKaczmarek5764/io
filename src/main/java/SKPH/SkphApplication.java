package SKPH;

import Chat.Chat;
import Chat.ChatClient;
import Chat.ChatServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = {"Chat"})
public class SkphApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SkphApplication.class, args);

        ///to get receive from server e.g. http://localhost:8080/chat/hello

        System.out.println("SKHP Server Started");
    }
}
