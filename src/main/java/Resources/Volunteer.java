package Resources;

import Classes.Report;
import Classes.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("volunteer")
public class Volunteer extends User implements Serializable {

    private boolean available = true;
    @ManyToOne
    @JoinColumn(name = "current_report_id")
    private Report currentReport;
    
    @OneToOne(mappedBy = "volunteer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Resource resource;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VolunteerEvaluation> evaluations = new ArrayList<>();

    @Transient
    private List<Report> completedReports = new ArrayList<>();

    public Volunteer() {
        super();
    }

    public Volunteer(String firstName, String lastName) {
        super(firstName, lastName);
        this.currentReport = null;
    }

    public Volunteer(String firstName, String lastName, String passwordHash, String email, String phoneNumber, LocalDate registrationDate) {
        super(firstName,lastName ,passwordHash , email, phoneNumber, registrationDate);
        this.currentReport = null;

    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Report getCurrentReport() {
        return currentReport;
    }

    public void setCurrentReport(Report currentReport) {
        this.currentReport = currentReport;
    }

    public void addCompletedReport(Report report) {
        this.completedReports.add(report);
    }

    public void removeCompletedReport(Report report) {
        this.completedReports.remove(report);
    }

    public List<VolunteerEvaluation> getEvaluations() {
        return Collections.unmodifiableList(evaluations);
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
                ", currentReport=" + currentReport +
                "} " + super.toString();
    }
}
