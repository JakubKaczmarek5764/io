package Resources;

import Classes.Report;
import Classes.reportStatus;
import db.ReportRepository;
import db.ResourcesRepository;
import db.UsersRepository;
import db.VolunteerEvaluationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/volunteer")
public class VolunteerService implements IVolunteer {
    private final ReportRepository reportRepository = new ReportRepository();
    private final VolunteerEvaluationRepository volunteerEvaluationRepository = new VolunteerEvaluationRepository();
    private final UsersRepository usersRepository = new UsersRepository();
    private final ResourcesRepository resourceRepository = new ResourcesRepository();


    public ResponseEntity<Void> assignVolunteer(@PathVariable long reportId, @PathVariable long volunteerId) {
        Report report = reportRepository.get(reportId);
        Volunteer volunteer = (Volunteer) usersRepository.get(volunteerId);

        if (report == null || volunteer == null || !volunteer.isAvailable()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        volunteer.setCurrentReport(report);

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

    public ResponseEntity<Volunteer> addVolunteer(@RequestBody Volunteer volunteer) {
        Resource resource = new Resource(volunteer);
        volunteer.setResource(resource);
        usersRepository.add(volunteer);
        return new ResponseEntity<>(volunteer, HttpStatus.CREATED);
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
        List<Report> reports = reportRepository.getAll();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    public ResponseEntity<List<Report>> getAvailableReports() {
        List<Report> reports = reportRepository.getAvailableReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    public ResponseEntity<List<Report>> getCompletedReports() {
        List<Report> reports = reportRepository.getCompletedReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    public ResponseEntity<List<Report>> getAssignedReports(@PathVariable long volunteerId) {
        List<Report> reports = reportRepository.getAssignedReports(volunteerId);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> finishReport(@PathVariable long reportId) {
        Report report = reportRepository.get(reportId);

        if (report == null) {
            return ResponseEntity.notFound().build();
        }

        report.setCompletion_date(new Date(System.currentTimeMillis()));
        report.setStatus(reportStatus.completed);

        for (Volunteer volunteer : report.getVolunteersList()) {
            volunteer.setCurrentReport(null);
            volunteer.setAvailable(true);

            volunteer.addCompletedReport(report);
            usersRepository.update(volunteer);
        }

        reportRepository.update(report);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<VolunteerEvaluation>> getVolunteerEvaluations(long volunteerId) {
        List<VolunteerEvaluation> allEvaluations = volunteerEvaluationRepository.getAll();

        List<VolunteerEvaluation> filteredEvaluations = allEvaluations.stream()
                .filter(evaluation -> evaluation.getVolunteer() != null
                        && evaluation.getVolunteer().getVolunteerId() == volunteerId)
                .collect(Collectors.toList());

        return new ResponseEntity<>(filteredEvaluations, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateVolunteer(@PathVariable long volunteerId, @RequestBody Volunteer updatedVolunteer) {
        Volunteer existingVolunteer = (Volunteer) usersRepository.get(volunteerId);

        if (existingVolunteer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedVolunteer.getFirstName() != null) {
            existingVolunteer.setFirstName(updatedVolunteer.getFirstName());
        }
        if (updatedVolunteer.getLastName() != null) {
            existingVolunteer.setLastName(updatedVolunteer.getLastName());
        }
        if (updatedVolunteer.getEvaluations() != null) {
            existingVolunteer.setEvaluations(updatedVolunteer.getEvaluations());
        }
        if (updatedVolunteer.getCurrentReport() != null) {
            existingVolunteer.setCurrentReport(updatedVolunteer.getCurrentReport());
        }
        if (updatedVolunteer.getResource() != null) {
            existingVolunteer.setResource(updatedVolunteer.getResource());
        }
        if (updatedVolunteer.getEmail() != null) {
            existingVolunteer.setEmail(updatedVolunteer.getEmail());
        }
        if (updatedVolunteer.getPhoneNumber() != null) {
            existingVolunteer.setPhoneNumber(updatedVolunteer.getPhoneNumber());
        }
        if (updatedVolunteer.getRegistrationDate() != null) {
            existingVolunteer.setRegistrationDate(updatedVolunteer.getRegistrationDate());
        }

        existingVolunteer.setAvailable(updatedVolunteer.isAvailable());

        usersRepository.update(existingVolunteer);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

