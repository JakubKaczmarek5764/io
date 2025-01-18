package Classes;

import db.ResourcesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public class ResourceService implements IResource {
    private final ResourcesRepository resourcesRepository = new ResourcesRepository();

    @GetMapping("/all")
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resourceList = resourcesRepository.getAll();
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(long id) {
        Resource resource = resourcesRepository.get(id);

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(long id, Resource updatedResource) {
        Resource resource = resourcesRepository.get(id);

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedResource.getType() != null) {
            resource.setType(updatedResource.getType());
        }

        resource.setQuantity(updatedResource.getQuantity());

        resourcesRepository.update(resource);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resource> deleteResource(long id) {
        Resource resource = resourcesRepository.get(id);

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        resourcesRepository.remove(id);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Resource> addResource(Resource resource) {
        resourcesRepository.add(resource);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/volunteers")
    public ResponseEntity<List<Resource>> getVolunteers() {
        List<Resource> volunteers = resourcesRepository.getVolunteers();
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }

}