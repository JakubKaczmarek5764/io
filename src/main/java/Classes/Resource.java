package Classes;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import static Classes.resourceType.DONATION;
import static Classes.resourceType.VOLUNTEER;

@Entity
@Table(name = "Resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int resource_id;

    @Column
    @Enumerated(EnumType.STRING)
    private resourceType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "volunteer_id", referencedColumnName = "userId")
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "donation_id")
    private Donation donation;

    @Column
    private int quantity;

    @Column
    private boolean available;



    public Resource(){}

    public Resource(Volunteer volunteer) {
        this.type = VOLUNTEER;
        this.volunteer = volunteer;
        this.quantity = 1;
        this.available = true;
    }

    public Resource(Donation donation) {
        this.type = DONATION;
        this.donation = donation;
        this.quantity = 1;
        this.available = true;
    }

    public Resource(resourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
        this.available = true;
    }
    public int getResource_id() {
        return resource_id;
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
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
        this.available = quantity > 0;
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
