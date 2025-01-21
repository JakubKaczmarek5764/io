
import Classes.Location;
import db.LocationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationsCRUDTest {

    private LocationRepository repo;
    private Location location;

    @BeforeEach
    public void setUp() {
        location = new Location("warszawski", "Warszawa", "Długa", "35A", "09-900", 52.2296756, 21.0122287);
        repo = new LocationRepository();
    }

    @AfterEach
    void cleanUp() {
        repo.getAll().forEach(location -> repo.remove(location.getLocation_id()));
    }

    @Test
    public void testAdd() {
        repo.add(location);
        Location returnedLocation = repo.get(location.getLocation_id());

        Assertions.assertEquals(location.getLocation_id(), returnedLocation.getLocation_id());
        Assertions.assertEquals(location.getRegion(), returnedLocation.getRegion());
        Assertions.assertEquals(location.getCity(), returnedLocation.getCity());
    }

    @Test
    public void testUpdate() {
        repo.add(location);
        Location returnedLocation = repo.get(location.getLocation_id());
        returnedLocation.setRegion("malopolskie");
        returnedLocation.setCity("Krakow");
        repo.update(returnedLocation);
        Location updatedLocation = repo.get(returnedLocation.getLocation_id());
        Assertions.assertTrue(updatedLocation.getRegion().equals("malopolskie"));
        Assertions.assertTrue(updatedLocation.getCity().equals("Krakow"));
        Assertions.assertFalse(updatedLocation.getRegion().equals(location.getRegion()));
    }

    @Test
    public void testRemove() {
        repo.add(location);
        repo.remove(location.getLocation_id());
        Location returnedLocation = repo.get(location.getLocation_id());
        Assertions.assertNull(returnedLocation);
    }

    @Test
    public void testGetAll() {
        repo.add(location);
        Location location2 = new Location("warszawski", "Warszawa", "Długa", "35A", "09-900", 53.2296756, 23.0122287);
        repo.add(location2);
        Assertions.assertEquals(2, repo.getAll().size());
        Assertions.assertEquals(location.getRegion(), repo.get(location.getLocation_id()).getRegion());
        Assertions.assertEquals(location.getCity(), repo.get(location.getLocation_id()).getCity());
    }
}