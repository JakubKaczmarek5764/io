package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface IVolunteer {

    @PutMapping("/assign/{taskId}/{volunteerId}")
    ResponseEntity<Void> assignVolunteer(@PathVariable long taskId, @PathVariable long volunteerId);

    @PostMapping("/evaluate/{volunteerId}/{taskId}")
    ResponseEntity<Void> evaluateVolunteer(@PathVariable long volunteerId, @PathVariable long taskId, @RequestBody VolunteerEvaluation evaluation);

    @PostMapping()
    ResponseEntity<Void> addVolunteer(@RequestBody Volunteer volunteer);

    @DeleteMapping("/{volunteerId}")
    ResponseEntity<Void> deleteVolunteer(@PathVariable long volunteerId);
}
