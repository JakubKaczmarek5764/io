package Classes;

import Resources.Resource;
import Resources.Volunteer;
import db.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
public class IReportImpl implements IReport, IVictim {

    private ReportRepository reportRepository = new ReportRepository();

    @Override
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reportList = reportRepository.getAll();
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Report> getReport(int id) {
        Report report = reportRepository.get(id);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Report> updateReport(int id, Report updatedReport) {
        Report report = reportRepository.get(id);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(updatedReport.getCategory() != null){
            report.setCategory(updatedReport.getCategory());
        }
        if(updatedReport.getVictim() != null){
            report.setVictim(updatedReport.getVictim());
        }
        if(updatedReport.getCharity() != null){
            report.setCharity(updatedReport.getCharity());
        }
        if (updatedReport.getLocation() != null){
            report.setLocation(updatedReport.getLocation());
        }
        if(updatedReport.getStatus() != null){
            report.setStatus(updatedReport.getStatus());
        }
        if(updatedReport.getReport_date() != null){
            report.setReport_date(updatedReport.getReport_date());
        }
        if (updatedReport.getAccept_date() != null){
            report.setAccept_date(updatedReport.getAccept_date());
        }
        if(updatedReport.getCompletion_date() != null){
            report.setCompletion_date(updatedReport.getCompletion_date());
        }
        if (updatedReport.getResourcesList() != null){
            report.getResourcesList().clear();
            for (Resource resource : updatedReport.getResourcesList()) {
                report.addResource(resource);
            }
        }
        if(updatedReport.getVolunteersList() != null){
            report.getVolunteersList().clear();
            for (Volunteer volunteer : updatedReport.getVolunteersList()) {
                report.addVolunteer(volunteer);
            }
        }
        reportRepository.update(report);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Report> deleteReport(int id) {
        Report report = reportRepository.get(id);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        reportRepository.remove(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Report>> getReportByOrganizationId(int id) {
        List<Report> reportList = reportRepository.getAll();
        List<Report> reportListUser = new ArrayList<>();
        for(Report report : reportList){
            if(report.getCharity().getCharity_id() == id){
                reportListUser.add(report);
            }
        }
        return new ResponseEntity<>(reportListUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Report>> getReportByVictimId(int id) {
        List<Report> reportList = reportRepository.getAll();
        List<Report> reportListUser = new ArrayList<>();
        for(Report report : reportList){
            if(report.getVictim().getUserId() == id){
                reportListUser.add(report);
            }
        }
        return new ResponseEntity<>(reportListUser, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Report> addReport(Report report) {
        reportRepository.add(report);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }
}
