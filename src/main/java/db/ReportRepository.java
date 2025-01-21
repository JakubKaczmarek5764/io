package db;

import Classes.Report;
import Classes.reportStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ReportRepository implements IRepository<Report> {
    @Override
    public void add(Report entity) {
        Repository.add(entity);
    }

    @Override
    public void remove(long id) {
        Repository.remove(Report.class, id);
    }

    @Override
    public void update(Report entity) {
        Repository.update(entity);
    }

    @Override
    public Report get(long id) {
        return Repository.get(Report.class, id);
    }

    @Override
    public List<Report> getAll() {
        return Repository.getAll(Report.class);
    }

    public List<Report> getAvailableReports() {
        return getAll().stream()
                .filter(report->report.getStatus() == reportStatus.accepted)
                .collect(Collectors.toList());
    }

    public List<Report> getCompletedReports() {
        return getAll().stream()
                .filter(report -> report.getStatus() == reportStatus.completed)
                .collect(Collectors.toList());
    }

    public List<Report> getAssignedReports(long volunteerId) {
        return getAll().stream()
                .filter(report -> report.getVolunteersList().stream()
                        .anyMatch(volunteer -> volunteer.getUserId() == volunteerId))
                .collect(Collectors.toList());
    }
}
