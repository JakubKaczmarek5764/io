package Classes;

import db.ReportRepository;
import db.UsersRepository;
import db.VolunteerEvaluationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
public class VolunteerService implements IVolunteer {
    private final ReportRepository reportRepository = new ReportRepository();
    private final VolunteerEvaluationRepository volunteerEvaluationRepository = new VolunteerEvaluationRepository();
    private final UsersRepository usersRepository = new UsersRepository();

    
    public ResponseEntity<Void> assignVolunteer(@PathVariable long reportId, @PathVariable long volunteerId) {
        Report report = reportRepository.get(reportId);
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);

        if (report == null || volunteer == null || !volunteer.isAvailable()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        volunteer.setAvailable(false);
        report.addVolunteer(volunteer);
        reportRepository.update(report);
        usersRepository.update(volunteer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> evaluateVolunteer(@PathVariable long volunteerId, @PathVariable long reportId, @RequestBody VolunteerEvaluation evaluation) {
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);
        Report report = reportRepository.get(reportId);

        if (volunteer == null || report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        volunteerEvaluationRepository.add(evaluation);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> addVolunteer(@RequestBody Volunteer volunteer) {
        usersRepository.add(volunteer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteVolunteer(@PathVariable long volunteerId) {
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);
        if (volunteer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usersRepository.remove(volunteerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<Report>> getReports() {
        List<Report> tasks = reportRepository.getAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<List<Report>> getAvailableReports() {
        List<Report> tasks = reportRepository.getAvailableReports();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<List<Report>> getCompletedReports() {
        List<Report> tasks = reportRepository.getCompletedReports();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<List<Report>> getAssignedReports(@PathVariable long volunteerId) {
        List<Report> reports = reportRepository.getAssignedReports(volunteerId);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

}
