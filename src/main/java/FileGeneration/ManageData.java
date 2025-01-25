package FileGeneration;

import Classes.*;
import db.*;

import java.time.LocalDate;
import java.util.Scanner;

public class ManageData {
    private final AuthoritiesRepository authoritiesRepo = new AuthoritiesRepository();
    private final CharityRepository charityRepo = new CharityRepository();
    private final DonationsRepository donationsRepo = new DonationsRepository();
    private final LocationRepository locationRepo = new LocationRepository();
    private final ResourcesRepository resourcesRepo = new ResourcesRepository();
    private final VolunteerEvaluationRepository evaluationRepo = new VolunteerEvaluationRepository();
    private final UsersRepository usersRepo = new UsersRepository();
    private final ReportRepository reportRepo = new ReportRepository();

    public void manageData() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = getUserChoice(scanner, "\nData Management Menu:",
                    "Add Default Data", "Remove All Data", "Back to Main Menu");

            switch (choice) {
                case 1 -> addDefaultData();
                case 2 -> removeAllData();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private int getUserChoice(Scanner scanner, String menuTitle, String... options) {
        System.out.println(menuTitle);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            return -1;
        }
        scanner.nextLine();
        return choice;
    }

    private void addDefaultData() {
        locationRepo.add(new Location("Mazowieckie", "Warsaw", "Marszałkowska", "1A", "00-001", 52.2297, 21.0122));
        locationRepo.add(new Location("Malopolskie", "Krakow", "Floriańska", "2B", "31-021", 50.0647, 19.9450));

        Charity charity1 = new Charity("Helping Hand", "Help for those in need");
        Charity charity2 = new Charity("Heart for Children", "Support for children in need");
        charityRepo.add(charity1);
        charityRepo.add(charity2);

        authoritiesRepo.add(new Authority("Jan", "Kowalski", "123456789", "Mazowieckie", "001", "jan.kowalski", "password1"));
        authoritiesRepo.add(new Authority("Anna", "Nowak", "987654321", "Malopolskie", "002", "anna.nowak", "password2"));

        User donor1;
        User donor2;
        donor1 = new Donator("Piotr", "Zawisza", charity1);
        donor2 = new Donator("Katarzyna", "Wisniewska", charity2);
        usersRepo.add(donor1);
        usersRepo.add(donor2);

        Victim user1 = new Victim("Jeremiasz", "Rożowy", charity1);
        Victim user2 = new Victim("Włodzimierz", "Biały", charity2);
        usersRepo.add(user1);
        usersRepo.add(user2);

        Volunteer volunteer1 = new Volunteer("Jan", "Kowalski", "hashedPassword123", "jan.kowalski@example.com", "123456789", LocalDate.now());
        Volunteer volunteer2 = new Volunteer("Anna", "Nowak", "hashedPassword456", "anna.nowak@example.com", "987654321", LocalDate.now());
        usersRepo.add(volunteer1);
        usersRepo.add(volunteer2);

        Resource resource1 = new Resource(resourceType.DONATION, 1, "Donation Resource");
        Resource resource2 = new Resource(resourceType.VOLUNTEER, 1, "Volunteer Resource");
        Resource resource3 = new Resource(resourceType.DONATION, 5, "Donation Resource");
        Resource resource4 = new Resource(resourceType.VEHICLE, 1, "VEHICLE Resource");
        resourcesRepo.add(resource3);
        resourcesRepo.add(resource4);

        Donation donation1 = new Donation((Donator) donor1, donationStatus.pending, new java.sql.Date(System.currentTimeMillis()), null);
        Donation donation2 = new Donation((Donator)donor2, donationStatus.accepted, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()));

        donation1.getResources().add(resource1);
        donation2.getResources().add(resource2);

        donationsRepo.add(donation1);
        donationsRepo.add(donation2);

        evaluationRepo.add(new VolunteerEvaluation(volunteer1, null, 5, "Great job!"));
        evaluationRepo.add(new VolunteerEvaluation(volunteer2, null, 4, "Nice."));

        reportRepo.add(new Report(user1, "Zaginął pies", charity1));
        reportRepo.add(new Report(user2, "Zaginął kot", charity2));

        System.out.println("Default data added successfully.");
    }

    private void removeAllData() {
        reportRepo.getAll().forEach(report -> reportRepo.remove(report.getReport_id()));
        evaluationRepo.getAll().forEach(evaluation -> evaluationRepo.remove(evaluation.getEvaluationId()));
        donationsRepo.getAll().forEach(donation -> donationsRepo.remove(donation.getDonation_id()));
        resourcesRepo.getAll().forEach(resource -> resourcesRepo.remove(resource.getResource_id()));
        usersRepo.getAll().forEach(user -> usersRepo.remove(user.getUserId()));
        authoritiesRepo.getAll().forEach(authority -> authoritiesRepo.remove(authority.getAuthorityId()));
        charityRepo.getAll().forEach(charity -> charityRepo.remove(charity.getCharity_id()));
        locationRepo.getAll().forEach(location -> locationRepo.remove(location.getLocation_id()));


        System.out.println("All data removed successfully.");
    }
}


