package Resources;

import db.ResourcesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/resource")
public class ResourceService implements IResource {
    private final ResourcesRepository resourcesRepository = new ResourcesRepository();

    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resourceList = resourcesRepository.getAll();
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }


    public ResponseEntity<Resource> getResourceById(@PathVariable long id) {
        Resource resource = resourcesRepository.get(id);

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    public ResponseEntity<Resource> updateResource(@PathVariable long id, @RequestBody Resource updatedResource) {
        Resource resource = resourcesRepository.get(id);

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            int quantityChange = updatedResource.getQuantity() - resource.getQuantity();
            resource.updateQuantity(quantityChange);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (updatedResource.getType() != null) {
            resource.setType(updatedResource.getType());
        }

        resource.setAvailable(resource.getQuantity() > 0);

        resourcesRepository.update(resource);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    public ResponseEntity<Resource> deleteResource(@PathVariable long id) {
        Resource resource = resourcesRepository.get(id);

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        resourcesRepository.remove(id);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    public ResponseEntity<Resource> addResource(@RequestBody Resource resource) {
        resourcesRepository.add(resource);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Resource>> getVolunteers() {
        List<Resource> volunteers = resourcesRepository.getVolunteers();
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }


}
