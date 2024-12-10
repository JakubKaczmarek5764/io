package Classes;

import com.sun.istack.NotNull;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int donation_id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @NotNull
    private Donator donator;
    @Enumerated(EnumType.STRING)
    private donationStatus status;
    private Date donationDate;
    private Date acceptDate;
    @OneToMany
    private List<Resource> resources = new ArrayList<>();

    public Donation() {

    }

    public Donation(int donation_id, Donator donator, donationStatus status, Date donationDate, Date acceptDate) {
        this.donation_id = donation_id;
        this.donator = donator;
        this.status = status;
        this.donationDate = donationDate;
        this.acceptDate = acceptDate;
    }

    public int getDonation_id() {
        return donation_id;
    }

    public Donator getDonator() {
        return donator;
    }

    public donationStatus getStatus() {
        return status;
    }

    public void setStatus(donationStatus status) {
        this.status = status;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) {

    }

    public void deleteResource(int id) {

    }

    public Resource getResource(int id) {
        return this.resources.get(id);
    }
}
