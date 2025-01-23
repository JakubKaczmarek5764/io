package Classes;

import com.sun.istack.NotNull;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("victim")
public class Victim extends User implements Serializable {

    @ManyToOne
    @NotNull
    private Charity charity;

    public Victim() {
    }

    public Victim(String firstName, String lastName, String loginHash, String passwordHash, String email, String phoneNumber, LocalDate registrationDate, LocalDate lastLogin, Charity charity) {
        super(firstName, lastName, loginHash, passwordHash, email, phoneNumber, registrationDate, lastLogin);
        this.charity = charity;
    }


    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }


    @Override
    public String toString() {
        return "Victim{" +
                "charity=" + charity +
                //", reportList=" + reportList +
                "} " + super.toString();
    }
}
