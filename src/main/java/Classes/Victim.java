package Classes;

import com.sun.istack.NotNull;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("victim")
public class Victim extends User{

    @ManyToOne
    @NotNull
    private Charity charity;

    @OneToMany
    private  List<Report> reportList = new ArrayList<>();

    public Victim(Charity charity) {
        this.charity = charity;
    }

    public Victim() {
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void addToReportList(Report report){
        this.reportList.add(report);
    }

    public Report getReportById(int id){
        return this.reportList.get(id);
    }
}
