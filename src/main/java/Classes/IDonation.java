package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IDonation {
    @GetMapping
    ResponseEntity<List<Donation>> getAllDonations();

    @GetMapping("/{id}")
    ResponseEntity<Donation> getDonationById(@PathVariable int id);

    @GetMapping("/charity/{id}")
    ResponseEntity<List<Donation>> getDonationByCharityId(@PathVariable int id);

    @GetMapping("/donator/{id}")
    ResponseEntity<List<Donation>> getDonationByDonatorId(@PathVariable int id);


    @PutMapping("/{id}")
    ResponseEntity<Donation> updateDonation(@PathVariable int id, @RequestBody Donation updatedDonation);

    @DeleteMapping("/{id}")
    ResponseEntity<Donation> deleteDonation(@PathVariable int id);
}
