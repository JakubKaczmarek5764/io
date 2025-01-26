//package FileGeneration;
//
//import SKPH.SkphApplication;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import java.util.Scanner;
//
//@SpringBootApplication
//@EnableScheduling
//@ComponentScan(basePackages = {"Chat", "Classes","FileGeneration"})
//public class MainFileGUI {
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(SkphApplication.class, args);
//        System.out.println("SKHP Server Started");
//
//        ReportGenerator reportGenerator = new ReportGenerator();
//        ReportGeneratorVisualizer visualizer = new ReportGeneratorVisualizer();
//        ManageData dataManagement = new ManageData();
//
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\nMain Menu:");
//            System.out.println("1. Generate Reports");
//            System.out.println("2. Manage Data");
//            System.out.println("3. Delete All Reports");
//            System.out.println("4. Exit");
//
//            int mainChoice = scanner.nextInt();
//
//            switch (mainChoice) {
//                case 1 -> {
//                    System.out.println("\nChoose a report category:");
//                    System.out.println("1. Donators");
//                    System.out.println("2. Volunteers");
//                    System.out.println("3. Victims");
//                    System.out.println("4. Resources");
//                    System.out.println("5. Authorities");
//                    System.out.println("6. Charities");
//                    System.out.println("7. Donations");
//                    System.out.println("8. Reports");
//                    System.out.println("9. Volunteer Evaluations");
//
//                    int categoryChoice = scanner.nextInt();
//                    scanner.nextLine();
//
//                    String selectedCategory = switch (categoryChoice) {
//                        case 1 -> "Donators";
//                        case 2 -> "Volunteers";
//                        case 3 -> "Victims";
//                        case 4 -> "Resources";
//                        case 5 -> "Authorities";
//                        case 6 -> "Charities";
//                        case 7 -> "Donations";
//                        case 8 -> "Reports";
//                        case 9 -> "Volunteer Evaluations";
//                        default -> {
//                            System.out.println("Invalid choice. Try again.");
//                            yield null;
//                        }
//                    };
//
//                    if (selectedCategory == null) break;
//
//                    System.out.println("\nChoose a report format:");
//                    System.out.println("1. PDF");
//                    System.out.println("2. CSV");
//                    System.out.println("3. JSON");
//
//                    int formatChoice = scanner.nextInt();
//                    scanner.nextLine();
//
//                    String format = switch (formatChoice) {
//                        case 1 -> "PDF";
//                        case 2 -> "CSV";
//                        case 3 -> "JSON";
//                        default -> {
//                            System.out.println("Invalid format choice. Try again.");
//                            yield null;
//                        }
//                    };
//
//                    if (format == null) break;
//
//                    reportGenerator.generateAndExportReport(selectedCategory, format);
//
//                    String fileName = selectedCategory.replace(" ", "_") + "_Report." + format.toLowerCase();
//                    System.out.println("Report exported to " + format + ": " + fileName);
//
//                }
//                case 2 -> dataManagement.manageData();
//                case 3 -> {
//                    ReportGeneratorVisualizer.deleteReportFiles();
//                    System.out.println("All reports have been deleted.");
//                }
//                case 4 -> {
//                    System.out.println("Application terminated.");
//                    scanner.close();
//                    return;
//                }
//                default -> System.out.println("Invalid choice. Try again.");
//            }
//        }
//    }
//}