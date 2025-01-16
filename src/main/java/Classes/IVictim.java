package Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IVictim {

    @PostMapping()
    ResponseEntity<Report> addReport(@RequestBody Report report);
}
