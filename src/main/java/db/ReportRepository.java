//package db;
//
//import Classes.Report;
//
//import java.util.List;
//
//public class ReportRepository implements IRepository<Report> {
//    @Override
//    public void add(Report entity) {
//        Repository.add(entity);
//    }
//
//    @Override
//    public void remove(long id) {
//        Repository.remove(Report.class, id);
//    }
//
//    @Override
//    public void update(Report entity) {
//        Repository.update(entity);
//    }
//
//    @Override
//    public Report get(long id) {
//        return Repository.get(Report.class, id);
//    }
//
//    @Override
//    public List<Report> getAll() {
//        return Repository.getAll(Report.class);
//    }
//}
