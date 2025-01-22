package Classes;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("donator")
public class Donator extends User {
    @ManyToOne
    private Charity charity;

    public Donator(String firstName, String lastName, String loginHash, String passwordHash, String email, String phoneNumber, LocalDate registrationDate, LocalDateTime lastLogin, Charity charity) {
        super(firstName, lastName, loginHash, passwordHash, email, phoneNumber, registrationDate, lastLogin);
        this.charity = charity;
    }

    public Donator(String firstName, String lastName, Charity charity) {
        super(firstName, lastName);
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