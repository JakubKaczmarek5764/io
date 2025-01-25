package FileGeneration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

public interface IReportGenerator {

    @PostMapping("/generateAndExportReport/{type}/{format}")
    ResponseEntity<Void> generateAndExportReport(@PathVariable String type, @PathVariable String format);

    @GetMapping("/generateReportToCSV/{type}")
    ResponseEntity<String> generateReportToCSV(@PathVariable String type);

    @GetMapping("/generateReportToPDF/{type}")
    ResponseEntity<String> generateReportToPDF(@PathVariable String type);

    @GetMapping("/generateReportToJSON/{type}")
    ResponseEntity<String> generateReportToJSON(@PathVariable String type);
}
