package Classes;

import Resources.Resource;
import Resources.Volunteer;
import com.sun.istack.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int report_id;

    @ManyToOne
    private Victim victim;

    @ManyToOne
    @NotNull
    private Charity charity;

    private String category;

    @ManyToOne
    private Location location;

    @Enumerated(EnumType.STRING)
    private reportStatus status;

    private Date report_date;

    private  Date accept_date;

    private Date completion_date;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Resource> resources = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private  List<Volunteer> volunteers = new ArrayList<>();

    public Report(Victim victim, String category, Charity charity) {
        this.victim = victim;
        this.category = category;
        this.charity = charity;
    }

    public Report() {
    }

    public int getReport_id() {
        return report_id;
    }

    public Victim getVictim() {
        return victim;
    }

    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    public Charity getCharity() {
        return charity;
    }

    public String getCategory() {
        return category;
    }

    public Location getLocation() {
        return location;
    }

    public reportStatus getStatus() {
        return status;
    }

    public Date getReport_date() {
        return report_date;
    }

    public Date getAccept_date() {
        return accept_date;
    }

    public List<Resource> getResourcesList() {
        return resources;
    }

    public List<Volunteer> getVolunteersList() {
        return volunteers;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStatus(reportStatus status) {
        this.status = status;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public void setAccept_date(Date accept_date) {
        this.accept_date = accept_date;
    }

    public Date getCompletion_date() {
        return completion_date;
    }

    public void setCompletion_date(Date completion_date) {
        this.completion_date = completion_date;
    }

    public void addResource(Resource resource){
        this.resources.add(resource);
    }

    public void addVolunteer(Volunteer volunteer){
        this.volunteers.add(volunteer);
    }

    public void deleteResource(int id){
        this.resources.remove(resources.get(id));
    }

    public void deleteVolunteer(int id){
        this.volunteers.remove(this.volunteers.get(id));
    }

    public Resource getResource(int id){
        return this.resources.get(id);
    }

    public Volunteer getVolunteer(int id){
        return this.volunteers.get(id);
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }
}
