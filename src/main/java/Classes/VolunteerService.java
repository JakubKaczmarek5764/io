package Classes;

import db.ResourcesRepository;
import db.UsersRepository;
import db.VolunteerEvaluationRepository;
import db.TaskRepository;

import java.util.List;

public class VolunteerService {
    private TaskRepository taskRepository = new TaskRepository();
    private ResourcesRepository resourcesRepository = new ResourcesRepository();
    private VolunteerEvaluationRepository volunteerEvaluationRepository = new VolunteerEvaluationRepository();
    private UsersRepository usersRepository = new UsersRepository();

    public void assignVolunteer(Task task, Volunteer volunteer) {
        volunteer.setAvailable(false);
        task.setVolunteer(volunteer);
        taskRepository.update(task);
    }

    public void evaluateVolunteer(Volunteer volunteer, Task task, int rating, String description) {
        VolunteerEvaluation volunteerEvaluation = new VolunteerEvaluation(volunteer, task, rating, description);
        volunteerEvaluationRepository.add(volunteerEvaluation);
    }


}
