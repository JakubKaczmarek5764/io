package skph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class SkphApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SkphApplication.class, args);

        System.out.println("skph.Chat system");
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Wybierz tryb: ");
        System.out.println("1. Tryb serwera");
        System.out.println("2. Tryb klienta");
        int mode = keyboard.nextInt();

        switch (mode) {
            case 1: // Tryb serwera
                System.out.println("1. Tryb serwera");
                ChatServer server = new ChatServer();
                server.initializeChats();
                server.start();
                break;

            case 2: // Tryb klienta
                System.out.println("2. Tryb klienta");
                ChatClient client = new ChatClient("localhost");

                System.out.println("Dostepne opcje:");
                System.out.println("1. Dolacz do chatu");
                System.out.println("2. Wyslij wiadomosc");
                System.out.println("3. Wyjdz");

                boolean running = true;
                while (running) {
                    System.out.println("\nWybierz opcje:");
                    int option = keyboard.nextInt();
                    keyboard.nextLine();

                    switch (option) {
                        case 1: // Dołączenie do chatu
                            System.out.print("Podaj ID chatu do dolaczenia: ");
                            long chatId = keyboard.nextLong();
                            keyboard.nextLine();

                            Chat chat = new Chat(chatId, "skph.Chat" + chatId, false);
                            client.joinChat(chat);
                            System.out.println("Dolaczono do chatu: " + chatId);
                            break;

                        case 2: // Wysłanie wiadomości
                            System.out.print("Podaj ID chatu: ");
                            long targetChatId = keyboard.nextLong();
                            keyboard.nextLine();

                            System.out.print("Wprowadz wiadomosc: ");
                            String message = keyboard.nextLine();

                            System.out.print("Podaj swoje imie: ");
                            String userName = keyboard.nextLine();

                            User user = new User(1, userName); // Stały ID dla uproszczenia
                            client.sendMessage(targetChatId, message, user);
                            System.out.println("Wyslano wiadomosc do chatu: " + targetChatId);
                            break;

                        case 3: // Wyjście
                            System.out.println("Wyjscie z programu.");
                            running = false;
                            break;

                        default:
                            System.out.println("Niepoprawna opcja. Sprobuj ponownie.");
                    }
                }
                break;

            default:
                System.out.println("Niepoprawny tryb.");
        }

        keyboard.close();
    }
}
