import Classes.Charity;
import Classes.Donation;
import Classes.Donator;
import db.CharityRepository;
import db.DonationsRepository;
import db.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Classes.donationStatus;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DonationsRepositoryTest {
    private DonationsRepository donationsRepository = new DonationsRepository();
    private UsersRepository usersRepository = new UsersRepository();
    private CharityRepository charityRepository = new CharityRepository();
    private Charity charity;
    private Donator donator;
    @BeforeEach
    void cleanUp() {
        donationsRepository.getAll().forEach(donation -> donationsRepository.remove(donation.getDonation_id()));
        usersRepository.getAll().forEach(user -> usersRepository.remove(user.getUserId()));
        charityRepository.getAll().forEach(charity -> charityRepository.remove(charity.getCharity_id()));
        charity = new Charity("testCharity", "desc");
        donator = new Donator("firstName", "lastName", charity);
        charityRepository.add(charity);
        usersRepository.add(donator);
    }
    @Test
    public void addTest() {
        Donation donation = new Donation(donator, donationStatus.pending, new Date(124, 1, 1), new Date(124, 1, 2));
        donationsRepository.add(donation);
        Donation returnedDonation = donationsRepository.get(donation.getDonation_id());
        assertEquals(donation.getDonation_id(), returnedDonation.getDonation_id());
        assertEquals(donation.getStatus(), returnedDonation.getStatus());
        assertEquals(donation.getDonationDate(), returnedDonation.getDonationDate());
        assertEquals(donation.getAcceptDate(), returnedDonation.getAcceptDate());
    }
    @Test
    public void removeTest() {
        Donation donation = new Donation(donator, donationStatus.pending, new Date(124, 1, 1), new Date(124, 1, 2));
        donationsRepository.add(donation);
        Donation returnedDonation = donationsRepository.get(donation.getDonation_id());
        assertEquals(donation.getDonation_id(), returnedDonation.getDonation_id());
        donationsRepository.remove(donation.getDonation_id());
        assert donationsRepository.get(donation.getDonation_id()) == null;
    }
    @Test
    public void getTest() {
        Donation donation = new Donation(donator, donationStatus.pending, new Date(124, 1, 1), new Date(124, 1, 2));
        donationsRepository.add(donation);
        Donation returnedDonation = donationsRepository.get(donation.getDonation_id());
        assertEquals(donation.getDonation_id(), returnedDonation.getDonation_id());
        assertEquals(donation.getStatus(), returnedDonation.getStatus());
        assertEquals(donation.getDonationDate(), returnedDonation.getDonationDate());
        assertEquals(donation.getAcceptDate(), returnedDonation.getAcceptDate());
    }
    @Test
    public void getAllTest() {
        Donation donation = new Donation(donator, donationStatus.pending, new Date(124, 1, 1), new Date(124, 1, 2));
        Donation donation2 = new Donation(donator, donationStatus.pending, new Date(124, 1, 1), new Date(124, 1, 2));
        donationsRepository.add(donation);
        donationsRepository.add(donation2);
        assertEquals(2, donationsRepository.getAll().size());
    }
}
