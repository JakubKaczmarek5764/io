package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ILocation {

    @PostMapping("addLocation")
    ResponseEntity<Location> addLocation(@RequestBody Location location);

    @GetMapping("/{id}")
    ResponseEntity<Location> getLocationById(@PathVariable int id);

    @GetMapping("/getAllLocations")
    ResponseEntity<List<Location>> getAllLocations();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLocation(@PathVariable int id);

}
