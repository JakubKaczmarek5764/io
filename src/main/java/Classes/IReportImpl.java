package Classes;

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

    ReportRepository reportRepository = new ReportRepository();

    @Override
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reportList = reportRepository.getAll();
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Report> getReport(int id) {
        Report report = reportRepository.get(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Report> updateReport(int id, Report updatedReport) {
        Report report = reportRepository.get(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Report> deleteReport(int id) {
        Report report = reportRepository.get(id);
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
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
