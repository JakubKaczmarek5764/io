package Classes;

import db.ResourcesRepository;
import db.UsersRepository;
import db.VolunteerEvaluationRepository;
import db.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
public class VolunteerService implements IVolunteer {
    private TaskRepository taskRepository = new TaskRepository();
    private VolunteerEvaluationRepository volunteerEvaluationRepository = new VolunteerEvaluationRepository();
    private UsersRepository usersRepository = new UsersRepository();

    
    public ResponseEntity<Void> assignVolunteer(@PathVariable long taskId, @PathVariable long volunteerId) {
        Task task = taskRepository.get(taskId);
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);

        if (task == null || volunteer == null || !volunteer.isAvailable()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        volunteer.setAvailable(false);
        task.setVolunteer(volunteer);
        taskRepository.update(task);
        usersRepository.update(volunteer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> evaluateVolunteer(@PathVariable long volunteerId, @PathVariable long taskId, @RequestBody VolunteerEvaluation evaluation) {
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);
        Task task = taskRepository.get(taskId);

        if (volunteer == null || task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        volunteerEvaluationRepository.add(evaluation);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> addVolunteer(@RequestBody Volunteer volunteer) {
        usersRepository.add(volunteer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteVolunteer(@PathVariable long volunteerId) {
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);
        if (volunteer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usersRepository.remove(volunteerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskRepository.getAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getAvailableTasks() {
        List<Task> tasks = taskRepository.getAvailableTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getCompletedTasks(@PathVariable long volunteerId) {
        List<Task> tasks = taskRepository.getCompletedTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
