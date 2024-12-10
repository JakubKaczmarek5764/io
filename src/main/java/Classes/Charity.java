package Classes;

import jakarta.persistence.*;

@Entity
@Table(name = "charities")
public class Charity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int charity_id;

    @Column
    private String charity_name;

    private String charity_description;

    public Charity() {

    }

    public int getCharity_id() {
        return charity_id;
    }

    public Charity(String charity_name, String charity_description) {
        this.charity_name = charity_name;
        this.charity_description = charity_description;
    }

    public String getCharity_name() {
        return charity_name;
    }

    public String getCharity_description() {
        return charity_description;
    }

    public void setCharity_name(String charity_name) {
        this.charity_name = charity_name;
    }

    public void setCharity_description(String charity_description) {
        this.charity_description = charity_description;
    }
}
