package Classes;

import db.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController implements ILocation{

    private final LocationRepository locationRepository = new LocationRepository();

    @Override
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        locationRepository.add(location);
        return ResponseEntity.ok(location);
    }

    @Override
    public ResponseEntity<Location> getLocationById(@PathVariable int id) {
        Location location = locationRepository.get(id);
        if (location == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.getAll();
        if (locations == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteLocation(@PathVariable int id) {
        if (locationRepository.get(id) == null) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        locationRepository.remove(id);
        if (locationRepository.get(id) == null) {return new ResponseEntity<>(HttpStatus.OK);}
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
