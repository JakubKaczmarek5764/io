package FileGeneration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reporting")
public class ReportGeneratorService implements IReportGenerator {

    private final ReportGenerator reportGenerator = new ReportGenerator();

    @PostMapping("/generateAndExportReport/{type}/{format}")
    public ResponseEntity<Void> generateAndExportReport(
            @PathVariable String type,
            @PathVariable String format) {

        try {
            reportGenerator.generateAndExportReport(type.toLowerCase(), format.toLowerCase());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generateReportToCSV/{type}")
    public ResponseEntity<String> generateReportToCSV(@PathVariable String type) {
        try {
            String csvContent = reportGenerator.generateReportToCSV(type.toLowerCase());
            return new ResponseEntity<>(csvContent, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generateReportToPDF/{type}")
    public ResponseEntity<String> generateReportToPDF(@PathVariable String type) {
        try {
            String pdfContent = reportGenerator.generateReportToPDF(type.toLowerCase());
            return new ResponseEntity<>(pdfContent, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generateReportToJSON/{type}")
    public ResponseEntity<String> generateReportToJSON(@PathVariable String type) {
        try {
            String jsonContent = reportGenerator.generateReportToJSON(type.toLowerCase());
            return new ResponseEntity<>(jsonContent, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}