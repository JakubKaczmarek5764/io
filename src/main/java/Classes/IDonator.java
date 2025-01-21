package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface IDonator {

    @PostMapping
    ResponseEntity<Donation> createDonation(@RequestBody Donation donation);

}
