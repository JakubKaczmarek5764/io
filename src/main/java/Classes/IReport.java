package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IReport {

    @GetMapping
    ResponseEntity<List<Report>> getAllReports();

    @GetMapping("/{id}")
    ResponseEntity<Report> getReport(@PathVariable int id);

    @PutMapping("/{id}")
    ResponseEntity<Report> updateReport(@PathVariable int id, @RequestBody Report updatedReport);

    @DeleteMapping("/{id}")
    ResponseEntity<Report> deleteReport(@PathVariable int id);

}
