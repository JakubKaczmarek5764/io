//import Classes.Charity;
//import Classes.Donation;
//import Classes.Donator;
//import Classes.donationStatus;
//import db.CharityRepository;
//import db.DonationsRepository;
//import db.UsersRepository;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Date;
//
//public class IDonationImplTest {
//    private DonationsRepository donationsRepository = new DonationsRepository();
//    private UsersRepository usersRepository = new UsersRepository();
//    private CharityRepository charityRepository = new CharityRepository();
//
//    Charity charity = new Charity("Pomoc","Pomagamy");
//    Donator donator = new Donator("Jan","Nowak",charity);
//    Donation donation = new Donation(donator, donationStatus.pending, Date.valueOf("2023-12-08"),null);
//
//    @Test
//    public void testDonation() {
//        charityRepository.add(charity);
//        usersRepository.add(donator);
//        donationsRepository.add(donation);
//    }
//}
