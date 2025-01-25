package Classes;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("donator")
public class Donator extends User {
    @ManyToOne
    private Charity charity;


    public Donator(String firstName, String lastName, Charity charity) {
        super(firstName, lastName);
        this.charity = charity;
    }

    public Donator(String firstName, String lastName, String passwordHash, String email, String phoneNumber, LocalDate registrationDate, Charity charity) {
        super(firstName, lastName, passwordHash, email, phoneNumber, registrationDate);
        this.charity = charity;
    }

    public Donator() {}

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

}