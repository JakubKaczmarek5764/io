import Classes.*;
import db.CharityRepository;
import db.ReportRepository;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class ReportCRUDTest {

    private UsersRepository userRepo;
    private ReportRepository reportRepo;
    private CharityRepository charityRepo;
    private Charity charity;
    private User user;
    private Report report;


    @BeforeEach
    public void setUp() {
        charity = new Charity("Caritas", "Pomoc dla bezdomnych");
        user = new Victim("Paweł", "Pietrzak", charity);
        report = new Report((Victim) user, "Zaginął pies", charity);
        userRepo = new UsersRepository();
        reportRepo = new ReportRepository();
        charityRepo = new CharityRepository();

    }
    @AfterEach
    void cleanUp() {
        reportRepo.getAll().forEach(report -> reportRepo.remove(report.getReport_id()));
        userRepo.getAll().forEach(user -> userRepo.remove(user.getUserId()));
        charityRepo.getAll().forEach(charity -> charityRepo.remove(charity.getCharity_id()));


    }

    @Test
    public void addTest() {
        charityRepo.add(charity);
        userRepo.add(user);
        reportRepo.add(report);
        Report returnedReport = reportRepo.get(report.getReport_id());
        Assertions.assertEquals(report.getReport_id(), returnedReport.getReport_id());
        Assertions.assertEquals(report.getVictim().getLastName(), returnedReport.getVictim().getLastName());
        Assertions.assertEquals(report.getCharity().getCharity_name(), returnedReport.getCharity().getCharity_name());
    }

    @Test
    public void removeTest() {
        charityRepo.add(charity);
        userRepo.add(user);
        reportRepo.add(report);
        reportRepo.remove(report.getReport_id());
        Report returnedReport = reportRepo.get(report.getReport_id());
        Assertions.assertNull(returnedReport);
    }

    @Test
    public void updateTest() {
        charityRepo.add(charity);
        userRepo.add(user);
        reportRepo.add(report);
        report.setCategory("Zaginął kot");
        reportRepo.update(report);
        Report returnedReport = reportRepo.get(report.getReport_id());
        Assertions.assertEquals(report.getReport_id(), returnedReport.getReport_id());
        Assertions.assertEquals(report.getCategory(), returnedReport.getCategory());
    }

    @Test
    public void getAllTest() {
        charityRepo.add(charity);
        userRepo.add(user);
        reportRepo.add(report);
        Report report2 = new Report((Victim) user, "Zaginął kot", charity);
        reportRepo.add(report2);
        Assertions.assertEquals(2, reportRepo.getAll().size());
    }

}
