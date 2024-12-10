package Classes;

import jakarta.persistence.*;

@Entity
@Table(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int location_id;

    @Column
    private String region;
    @Column
    private String city;

    public Location(String region, String city) {
        this.region = region;
        this.city = city;
    }
    public Location() {
    }

    public int getLocation_id() {
        return location_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
