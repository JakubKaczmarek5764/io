package SKPH;

import Chat.Chat;
import Chat.ChatClient;
import Chat.ChatServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SkphApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SkphApplication.class, args);

        ///todo create a start server

        System.out.println("SKHP Server Started");
    }
}
