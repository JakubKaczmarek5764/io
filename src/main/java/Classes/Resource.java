package Classes;

import jakarta.persistence.*;

@Entity
@Table(name = "Resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int resource_id;

    @Enumerated(EnumType.STRING)
    private resourceType type;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "volunteer_id")
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "donation_id")
    private Donation donation;

    private int quantity;

    private boolean available;

    public int getResource_id() {
        return resource_id;
    }

    public Resource(){}

    public Resource(resourceType type, Volunteer volunteer, int quantity) {
        this.type = type;
        this.volunteer = volunteer;
        this.quantity = quantity;
        this.available = true;
    }

    public Resource(resourceType type,Donation donation, int quantity) {
        this.type = type;
        this.donation = donation;
        this.quantity = quantity;
        this.available = true;
    }

    public Resource(resourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
        this.available = true;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
    public Donation getDonation() {
        return donation;
    }
    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public int getResourceId() {
        return resource_id;
    }

    public resourceType getType() {
        return type;
    }

    public void setType(resourceType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateQuantity(int change) {
        if (this.quantity + change < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative. Current: " + this.quantity + ", Change: " + change);
        }
        this.quantity += change;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
