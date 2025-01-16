package Classes;

import db.ResourcesRepository;
import db.UsersRepository;
import db.VolunteerEvaluationRepository;
import db.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class VolunteerService implements IVolunteer {
    private TaskRepository taskRepository = new TaskRepository();
    private ResourcesRepository resourcesRepository = new ResourcesRepository();
    private VolunteerEvaluationRepository volunteerEvaluationRepository = new VolunteerEvaluationRepository();
    private UsersRepository usersRepository = new UsersRepository();

    @PutMapping("/assign/{taskId}/{volunteerId}")
    public ResponseEntity<Void> assignVolunteer(@PathVariable long taskId, @PathVariable long volunteerId) {
        Task task = taskRepository.get(taskId);
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);

        if (task == null || volunteer == null || !volunteer.isAvailable()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        volunteer.setAvailable(false);
        task.setVolunteer(volunteer);
        taskRepository.update(task);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/evaluate/{volunteerId}/{taskId}")
    public ResponseEntity<Void> evaluateVolunteer(@PathVariable long volunteerId, @PathVariable long taskId, @RequestBody VolunteerEvaluation evaluation) {
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);
        Task task = taskRepository.get(taskId);

        if (volunteer == null || task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        volunteerEvaluationRepository.add(evaluation);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
