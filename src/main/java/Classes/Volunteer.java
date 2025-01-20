package Classes;

import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("volunteer")
public class Volunteer extends User implements Serializable {

    private boolean available;
    @ManyToOne
    @JoinColumn(name = "current_report_id")
    private Report currentReport;

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
        this.currentReport = null;
        this.evaluations = new ArrayList<>();
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
