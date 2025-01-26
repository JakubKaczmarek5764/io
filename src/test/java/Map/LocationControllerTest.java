//package Map;
//
//import Classes.Location;
//import Classes.LocationController;
//import db.LocationRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LocationControllerTest {
//
//    private LocationController locationController;
//    private LocationRepository repo = new LocationRepository();
//
//    @BeforeEach
//    void setUp() {
//        locationController = new LocationController(new LocationService(repo));
//    }
//
//    @AfterEach
//    void tearDown() {
//        // Usuwanie wszystkich rekordów z tabeli Location
//        List<Location> allLocations = repo.getAll();
//        for (Location location : allLocations) {
//            repo.remove(location.getLocation_id());
//        }
//
//    }
//
//    @Test
//    void testAddLocation() {
//        Location location = new Location("test", "Łódź", "Piotrkowska", "12", "90-001", 51.747169, 19.453329);
//        ResponseEntity<Location> response = locationController.addLocation(location);
//
//        //String region, String city, String street, String number, String zipCode, Double latitude, Double longitude
//
//        assertNotNull(response);
//        assertEquals(200, response.getStatusCodeValue()); // Sprawdzenie kodu statusu HTTP
//        assertNotNull(response.getBody());
//        assertEquals("Łódź", response.getBody().getCity());
//    }
//
//
//    @Test
//    void testGetLocationById() {
//        Location location = new Location("test", "Łódź", "Piotrkowska", "12", "90-001", 51.747169, 19.453329);
//        locationController.addLocation(location);
//
//        ResponseEntity<Location> response = locationController.getLocationById(location.getLocation_id());
//
//        assertNotNull(response);
//        assertEquals(200, response.getStatusCodeValue()); // Sprawdzenie kodu statusu HTTP
//        assertNotNull(response.getBody());
//        assertEquals("Łódź", response.getBody().getCity());
//    }
//
//
//    @Test
//    void testGetAllLocations() {
//        Location location1 = new Location("test", "Łódź", "Piotrkowska", "12", "90-001", 51.747169, 19.453329);
//        Location location2 = new Location("test", "Łódź", "Piotrkowska", "12", "90-001", 51.747169, 19.453329);
//
//        locationController.addLocation(location1);
//        locationController.addLocation(location2);
//
//        ResponseEntity<List<Location>> response = locationController.getAllLocations();
//
//        assertNotNull(response);
//        assertEquals(200, response.getStatusCodeValue()); // Sprawdzenie kodu statusu HTTP
//        assertNotNull(response.getBody());
//        assertEquals(2, response.getBody().size());
//    }
//
//    @Test
//    void testDeleteLocation() {
//        Location location = new Location("test", "Łódź", "Piotrkowska", "12", "90-001", 51.747169, 19.453329);
//        locationController.addLocation(location);
//
//        ResponseEntity<Void> response = locationController.deleteLocation(location.getLocation_id());
//        assertEquals(204, response.getStatusCodeValue()); // Sprawdzenie kodu statusu HTTP
//
//        ResponseEntity<Location> fetchedResponse = locationController.getLocationById(location.getLocation_id());
//        //assertEquals(404, fetchedResponse.getStatusCodeValue()); // Sprawdzenie, czy lokalizacja została usunięta
//    }
//
//
//}
