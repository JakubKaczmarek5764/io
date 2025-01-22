package Classes;

import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("volunteer")
public class Volunteer extends User implements Serializable {

    private boolean available = true;
    @ManyToOne
    @JoinColumn(name = "current_report_id")
    private Report currentReport;

    @OneToOne(mappedBy = "volunteer")
    private Resource resource;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VolunteerEvaluation> evaluations;

    @Transient
    private List<Report> completedReports;

    public Volunteer() {
        super();
    }

    public Volunteer(String firstName, String lastName, String loginHash, String passwordHash, String email, String phoneNumber, LocalDate registrationDate, LocalDate lastLogin) {
        super(firstName, lastName, loginHash, passwordHash, email, phoneNumber, registrationDate, lastLogin);
    }

    public Volunteer(String firstName, String lastName, String loginHash, String passwordHash, String email, String phoneNumber, LocalDate registrationDate, LocalDate lastLogin, boolean available, Report currentReport, Resource resource, List<VolunteerEvaluation> evaluations, List<Report> completedReports) {
        super(firstName, lastName, loginHash, passwordHash, email, phoneNumber, registrationDate, lastLogin);
        this.available = available;
        this.currentReport = currentReport;
        this.resource = resource;
        this.evaluations = evaluations;
        this.completedReports = completedReports;
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
                ", currentReport=" + currentReport +
                "} " + super.toString();
    }
}
