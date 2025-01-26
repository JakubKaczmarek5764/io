package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ILocation {

    @PostMapping("addLocation")
    ResponseEntity<Location> addLocation(@RequestBody Location location);

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable int id);

    @GetMapping("/getAllLocations")
    public ResponseEntity<List<Location>> getAllLocations();

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable int id);

}
