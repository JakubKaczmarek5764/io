package Classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int locationId;

    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90.0")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90.0")
    @Column(name = "latitude", precision = 8, nullable = false)
    private double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180.0")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180.0")
    @Column(name = "longitude", precision = 9, nullable = false)
    private double longitude;

    @NotBlank(message = "City is required")
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank(message = "Street is required")
    @Column(name = "street", nullable = false)
    private String street;

    @NotBlank(message = "Building number is required")
    @Column(name = "number", nullable = false)
    private String number;

    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Zip code must be in format XX-XXX")
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    // Konstruktor domyślny wymagany przez JPA
    public Location() {
    }

    // Konstruktor pełny
    public Location(double latitude, double longitude, String city, String street, String number, String zipCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
    }

    // Gettery i settery
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
