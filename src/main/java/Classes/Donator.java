package Classes;

import jakarta.persistence.*;


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

    public Donator() {}

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

}