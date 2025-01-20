package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface IVolunteer {

    @PutMapping("/assign/{taskId}/{volunteerId}")
    ResponseEntity<Void> assignVolunteer(@PathVariable long taskId, @PathVariable long volunteerId);

    @PostMapping("/evaluate/{volunteerId}/{taskId}")
    ResponseEntity<Void> evaluateVolunteer(@PathVariable long volunteerId, @PathVariable long taskId, @RequestBody VolunteerEvaluation evaluation);

    @PostMapping()
    ResponseEntity<Void> addVolunteer(@RequestBody Volunteer volunteer);

    @DeleteMapping("/{volunteerId}")
    ResponseEntity<Void> deleteVolunteer(@PathVariable long volunteerId);

    @GetMapping()
    ResponseEntity<List<Task>> getTasks();

    @GetMapping("/available")
    ResponseEntity<List<Task>> getAvailableTasks();

    @GetMapping("/completed")
    ResponseEntity<List<Task>> getCompletedTasks();

    @GetMapping("/{volunteerId}")
    ResponseEntity<List<Task>> getAssignedTasks(@PathVariable long volunteerId);
}
