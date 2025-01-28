package Resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IResource {
    @GetMapping()
    ResponseEntity<List<Resource>> getAllResources();

    @GetMapping("/{id}")
    ResponseEntity<Resource> getResourceById(@PathVariable long id);

    @PutMapping("/{id}")
    ResponseEntity<Resource> updateResource(@PathVariable long id, @RequestBody Resource updatedResource);

    @DeleteMapping("/{id}")
    ResponseEntity<Resource> deleteResource(@PathVariable long id);

    @PostMapping()
    ResponseEntity<Resource> addResource(@RequestBody Resource resource);

    @GetMapping("/volunteers")
    ResponseEntity<List<Resource>> getVolunteers();
}
