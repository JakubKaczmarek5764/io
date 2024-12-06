package org.example;

import com.sun.istack.NotNull;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("donator")
public class Donator extends User {
    @ManyToOne
    @NotNull
    private Charity charity;
    @OneToMany
    private List<Donation> donationList = new ArrayList<>();


    public Donator(Charity charity) {
        this.charity = charity;
    }

    public Donator() {}

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public List<Donation> getDonationList() {
        return donationList;
    }

    public void addDonation(Donation donation){

    }

    public void getDonationById(int id) {

    }
}
