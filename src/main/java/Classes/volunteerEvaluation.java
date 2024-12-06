package Classes;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "volunteerEvaluation")
public class volunteerEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int evaluationId;
    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "volunteer_id")
    private Volunteer volunteer;
    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private Task task;
    private int rating;
    private String description;
    private Date evaluationDate;

    public volunteerEvaluation() {
    }

    public volunteerEvaluation(int evaluationId, Volunteer volunteer, Task task, int rating, String description, Date evaluationDate) {

        this.evaluationId = evaluationId;
        this.volunteer = volunteer;
        this.task = task;
        this.rating = rating;
        this.description = description;
        this.evaluationDate = evaluationDate;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getEvaluation() {
        return "Task ID: " + (task != null ? task.getTaskId() : "N/A") + ", Rating: " + rating + ", Description: " + description;
    }

}