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
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Volunteer volunteer;
    private String description;
    @Enumerated(EnumType.STRING)
    private status status;
    private Date startDate;
    private Date completedDate;

    public Task(String descriptions, status status) {
        this.volunteer = null;
        this.description = description;
        this.status = status;


    }
    public Task(){}

    public status getStatus() {
        return status;
    }

    public void setStatus(status status) {
        this.status = status;
    }

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
