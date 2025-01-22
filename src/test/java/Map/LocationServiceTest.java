package Map;

import Classes.Location;
import db.LocationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocationServiceTest {

    private LocationService locationService;
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        locationRepository = new LocationRepository();
        locationService = new LocationService(locationRepository);
    }

    @AfterEach
    void tearDown() {
        // Usuwanie wszystkich rekordów z tabeli Location
        List<Location> allLocations = locationRepository.getAll();
        for (Location location : allLocations) {
            locationRepository.remove(location.getLocationId());
        }
    }

    @Test
    void testAddLocation() {
        Location location = new Location(51.747169, 19.453329, "Łódź", "Piotrkowska", "12", "90-001");
        locationService.addLocation(location);

        Location fetchedLocation = locationService.getLocationById(location.getLocationId());
        assertNotNull(fetchedLocation);
        assertEquals("Łódź", fetchedLocation.getCity());
    }

    @Test
    void testGetAllLocations() {
        Location location1 = new Location(51.747169, 19.453329, "Łódź", "Piotrkowska", "12", "90-001");
        Location location2 = new Location(52.229676, 21.012229, "Warszawa", "Marszałkowska", "3", "00-001");

        locationService.addLocation(location1);
        locationService.addLocation(location2);

        List<Location> locations = locationService.getAllLocations();
        assertEquals(2, locations.size());
    }

    @Test
    void testDeleteLocation() {
        Location location = new Location(51.747169, 19.453329, "Łódź", "Piotrkowska", "12", "90-001");
        locationService.addLocation(location);

        locationService.deleteLocation(location.getLocationId());

        Location deletedLocation = locationService.getLocationById(location.getLocationId());
        assertNull(deletedLocation);
    }
}
