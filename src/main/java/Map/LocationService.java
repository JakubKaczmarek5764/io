package Map;

import Classes.Location;
import db.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location addLocation(Location location) {
        locationRepository.add(location);
        return location;
    }

    public Location getLocationById(int id) {
        return locationRepository.get(id);
    }

    public List<Location> getAllLocations() {
        return locationRepository.getAll();
    }

    public void deleteLocation(int id) {
        locationRepository.remove(id);
    }
}
