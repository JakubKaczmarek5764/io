package Classes;

import Resources.Resource;
import db.DonationsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/donation")
public class IDonationImpl implements IDonation, IDonator {

    private DonationsRepository donationsRepository = new DonationsRepository();


    @Override
    public ResponseEntity<List<Donation>> getAllDonations() {
        List<Donation> donationList = donationsRepository.getAll();
        return new ResponseEntity<>(donationList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Donation> getDonationById(int id) {
        Donation donation = donationsRepository.get(id);

        if (donation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Donation>> getDonationByCharityId(int id) {
        List<Donation> donationList = donationsRepository.getAll();
        List<Donation> donationListCharity = new ArrayList<>();
        for(Donation donation : donationList){
            if(donation.getDonator().getCharity().getCharity_id() == id){
                donationListCharity.add(donation);
            }
        }
        return new ResponseEntity<>(donationListCharity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Donation>> getDonationByDonatorId(int id) {
        List<Donation> donationList = donationsRepository.getAll();
        List<Donation> donationListDonator = new ArrayList<>();
        for(Donation donation : donationList){
            if(donation.getDonator().getUserId() == id){
                donationListDonator.add(donation);
            }
        }
        return new ResponseEntity<>(donationListDonator, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Donation> updateDonation(int id, Donation updatedDonation) {
        Donation donation = donationsRepository.get(id);

        if (donation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(updatedDonation.getDonator() != null){
            donation.setDonator(updatedDonation.getDonator());
        }
        if(updatedDonation.getStatus() != null){
            donation.setStatus(updatedDonation.getStatus());
        }
        if(updatedDonation.getDonationDate() != null){
            donation.setDonationDate(updatedDonation.getDonationDate());
        }
        if(updatedDonation.getAcceptDate() != null){
            donation.setAcceptDate(updatedDonation.getAcceptDate());
        }
        if(updatedDonation.getResources() != null){
            donation.getResources().clear();
            for (Resource resource : updatedDonation.getResources()) {
                donation.addResource(resource);
            }
        }
        donationsRepository.update(donation);
        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Donation> deleteDonation(int id) {
        Donation donation = donationsRepository.get(id);

        if (donation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        donationsRepository.remove(id);
        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Donation> createDonation(Donation donation) {
        donationsRepository.add(donation);
        return new ResponseEntity<>(donation, HttpStatus.CREATED);
    }
}
