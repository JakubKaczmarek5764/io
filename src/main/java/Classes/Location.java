package Classes;

import jakarta.persistence.*;

@Entity
@Table(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int location_id;

    public int getLocation_id() {
        return location_id;
    }
}
