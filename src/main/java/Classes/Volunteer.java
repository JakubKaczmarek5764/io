package Classes;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("volunteer")
public class Volunteer extends User implements Serializable {

    private boolean available;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> completedTasks;

    @OneToOne
    @JoinColumn(name = "current_task_id")
    private Task currentTask;

    @OneToOne(mappedBy = "volunteer")
    private Resource resource;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VolunteerEvaluation> evaluations;

    public Volunteer() {
        super();
    }

    public Volunteer(String firstName, String lastName) {
        super(firstName,lastName);
        this.available = true;
        this.completedTasks = new ArrayList<>();
        this.currentTask = null;
        this.evaluations = new ArrayList<>();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(List<Task> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public List<VolunteerEvaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<VolunteerEvaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public int getVolunteerId() {
        return getUserId();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "available=" + available +
                //", completedTasks=" + completedTasks +
                ", currentTask=" + currentTask +
                //", evaluations=" + evaluations +
                "} " + super.toString();
    }
}
