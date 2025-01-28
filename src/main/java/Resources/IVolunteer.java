package Resources;

import Classes.Report;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface IVolunteer {

    @PutMapping("/assign/{reportId}/{volunteerId}")
    ResponseEntity<Void> assignVolunteer(@PathVariable long reportId, @PathVariable long volunteerId);

    @PostMapping("/evaluate/{volunteerId}/{reportId}")
    ResponseEntity<Void> evaluateVolunteer(@PathVariable long volunteerId, @PathVariable long reportId, @RequestBody VolunteerEvaluation evaluation);

    @PostMapping()
    ResponseEntity<Volunteer> addVolunteer(@RequestBody Volunteer volunteer);

    @DeleteMapping("/{volunteerId}")
    ResponseEntity<Void> deleteVolunteer(@PathVariable long volunteerId);

    @GetMapping()
    ResponseEntity<List<Report>> getReports();

    @GetMapping("/available")
    ResponseEntity<List<Report>> getAvailableReports();

    @GetMapping("/completed")
    ResponseEntity<List<Report>> getCompletedReports();

    @GetMapping("/{volunteerId}")
    ResponseEntity<List<Report>> getAssignedReports(@PathVariable long volunteerId);

    @PutMapping("/finish/{reportId}")
    ResponseEntity<Void> finishReport(@PathVariable long reportId);

    @GetMapping("{volunteerId}/evaluations")
    ResponseEntity<List<VolunteerEvaluation>> getVolunteerEvaluations(@PathVariable long volunteerId);

    @PutMapping("/update/{volunteerId}")
    ResponseEntity<Void> updateVolunteer(@PathVariable long volunteerId, @RequestBody Volunteer updatedVolunteer);
}
