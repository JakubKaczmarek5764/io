package Classes;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("volunteer")
public class Volunteer extends User{
    private boolean available;
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> completedTasks;

    @ManyToOne
    @JoinColumn(name = "current_task_id")
    private Task currentTask;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<volunteerEvaluation> evaluations;

    public Volunteer() {
        super();
    }

    // Parameterized constructor
    public Volunteer(boolean available, List<Task> completedTasks, Task currentTask, List<volunteerEvaluation> evaluations) {
        super();
        this.available = available;
        this.completedTasks = completedTasks;
        this.currentTask = currentTask;
        this.evaluations = evaluations;
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

    public List<volunteerEvaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<volunteerEvaluation> evaluations) {
        this.evaluations = evaluations;
    }

}
