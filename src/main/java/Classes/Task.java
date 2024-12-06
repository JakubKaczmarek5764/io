package Classes;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int task_id;
    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "volunteer_id")
    private Volunteer volunteer;
    private String description;
    private String status;
    private Date startDate;
    private Date completedDate;

    public Task(int task_id, Volunteer volunteer, String description, String status) {

        this.task_id = task_id;
        this.volunteer = volunteer;
        this.description = description;
        this.status = status;


    }
    public Task(){}

    public int getTaskId() {
        return task_id;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

}
