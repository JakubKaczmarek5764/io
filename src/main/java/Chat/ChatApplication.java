//package Chat;
//
//import Resources.Volunteer;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import java.util.Scanner;
//import Classes.User;
//import Resources.Volunteer;
//import Classes.Victim;
//
//@SpringBootApplication(scanBasePackages = {"Chat"})
//public class ChatApplication {
//    public static void main(String[] args) throws Exception {
//        //SpringApplication.run(ChatApplication.class, args);
//
//        System.out.println("Chat system");
//        Scanner keyboard = new Scanner(System.in);
//
//        System.out.println("Wybierz tryb: ");
//        System.out.println("1. Tryb serwera");
//        System.out.println("2. Tryb klienta");
//        int mode = keyboard.nextInt();
//
//        switch (mode) {
//            case 1: // Tryb serwera
//                System.out.println("1. Tryb serwera");
//                ChatServer server = new ChatServer();
//                server.start();
//                break;
//
//            case 2: // Tryb klienta
//                keyboard = new Scanner(System.in);
//                System.out.println("2. Tryb klienta");
//
//                System.out.print("Podaj swoje imie: ");
//                String userFName = keyboard.nextLine();
//
//                System.out.print("Podaj swoje nazwisko: ");
//                String userLName = keyboard.nextLine();
//
//                Volunteer user = new Volunteer(userFName, userLName);
//                ChatClient client = new ChatClient();
//                client.createConnection("localhost", user, null);
//
//                System.out.println("Dostepne opcje:");
//                System.out.println("1. Dolacz do chatu");
//                System.out.println("2. Wyslij wiadomosc");
//                System.out.println("3. Wyjdz");
//
//                boolean running = true;
//                while (running) {
//                    System.out.println("\nWybierz opcje:");
//                    int option = keyboard.nextInt();
//                    keyboard.nextLine();
//
//                    switch (option) {
//                        case 1: // Dołączenie do chatu
//                            System.out.print("Podaj ID chatu do dolaczenia: ");
//                            long chatId = keyboard.nextLong();
//                            keyboard.nextLine();
//
//                            Chat chat = new Chat(chatId, "Chat" + chatId, false);
//                            client.joinChat(chat);
//                            System.out.println("Dolaczono do chatu: " + chatId);
//                            break;
//
//                        case 2: // Wysłanie wiadomości
//                            System.out.print("Podaj ID chatu: ");
//                            long targetChatId = keyboard.nextLong();
//                            keyboard.nextLine();
//
//                            System.out.print("Wprowadz wiadomosc: ");
//                            String message = keyboard.nextLine();
//
//                            client.sendMessage(targetChatId, message);
//                            System.out.println("Wyslano wiadomosc do chatu: " + targetChatId);
//                            break;
//
//                        case 3: // Wyjście
//                            System.out.println("Wyjscie z programu.");
//                            running = false;
//                            break;
//
//                        default:
//                            System.out.println("Niepoprawna opcja. Sprobuj ponownie.");
//                    }
//                }
//                break;
//
//            default:
//                System.out.println("Niepoprawny tryb.");
//        }
//
//        keyboard.close();
//    }
//}
