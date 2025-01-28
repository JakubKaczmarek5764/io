package FileGeneration;

import Classes.*;
import Resources.Resource;
import Resources.Volunteer;
import Resources.VolunteerEvaluation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import db.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {

    private final UsersRepository usersRepository = new UsersRepository();
    private final ReportRepository reportRepository = new ReportRepository();
    private final CharityRepository charityRepository = new CharityRepository();
    private final DonationsRepository donationsRepository = new DonationsRepository();
    private final AuthoritiesRepository authoritiesRepository = new AuthoritiesRepository();
    private final ResourcesRepository resourcesRepository = new ResourcesRepository();
    private final VolunteerEvaluationRepository evaluationsRepository = new VolunteerEvaluationRepository();
    private final LocationRepository locationRepository = new LocationRepository();

    private <T> List<T> fetchDataFromDatabase(Class<T> entityClass) {
        if (entityClass.equals(User.class) || entityClass.equals(Victim.class) || entityClass.equals(Donator.class) || entityClass.equals(Volunteer.class)) {
            return usersRepository.getAll().stream()
                    .filter(entityClass::isInstance)
                    .map(entityClass::cast)
                    .toList();
        } else if (entityClass.equals(Report.class)) {
            return (List<T>) reportRepository.getAll();
        } else if (entityClass.equals(Charity.class)) {
            return (List<T>) charityRepository.getAll();
        } else if (entityClass.equals(Donation.class)) {
            return (List<T>) donationsRepository.getAll();
        } else if (entityClass.equals(Authority.class)) {
            return (List<T>) authoritiesRepository.getAll();
        } else if (entityClass.equals(Resource.class)) {
            return (List<T>) resourcesRepository.getAll();
        } else if (entityClass.equals(VolunteerEvaluation.class)) {
            return (List<T>) evaluationsRepository.getAll();
        }
        throw new IllegalArgumentException("Unsupported entity class: " + entityClass.getSimpleName());
    }

    public void generateAndExportReport(String type, String format) {
        String reportContent = switch (format.toLowerCase()) {
            case "csv" -> generateReportToCSV(type);
            case "pdf" -> generateReportToPDF(type);
            case "json" -> generateReportToJSON(type);
            default -> null;
        };

        if (reportContent != null) {
            exportReport(reportContent, type + " Report", format);
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    public String generateReportToCSV(String type) {
        StringBuilder reportContent = new StringBuilder();

        switch (type.toLowerCase()) {
            case "volunteers" -> {
                List<Volunteer> volunteers = fetchDataFromDatabase(Volunteer.class);
                reportContent.append("Id,Name,LastName,Availability,Email,Phone,CurrentReport\n");
                for (Volunteer v : volunteers) {
                    reportContent.append(v.getVolunteerId()).append(",")
                            .append(v.getFirstName()).append(",")
                            .append(v.getLastName()).append(",")
                            .append(v.isAvailable()).append(",")
                            .append(v.getEmail()).append(",")
                            .append(v.getPhoneNumber()).append(",")
                            .append(v.getCurrentReport() != null ? v.getCurrentReport().getReport_id() : "None")
                            .append("\n");
                }
            }
            case "victims" -> {
                List<Victim> victims = fetchDataFromDatabase(Victim.class);
                reportContent.append("Id,Name,LastName,Email,Phone,Charity\n");
                for (Victim v : victims) {
                    reportContent.append(v.getUserId()).append(",")
                            .append(v.getFirstName()).append(",")
                            .append(v.getLastName()).append(",")
                            .append(v.getEmail()).append(",")
                            .append(v.getPhoneNumber()).append(",")
                            .append(v.getCharity() != null ? v.getCharity().getCharity_name() : "None")
                            .append("\n");
                }
            }
            case "donators" -> {
                List<Donator> donators = fetchDataFromDatabase(Donator.class);
                reportContent.append("Id,Name,LastName,Email,Phone,Charity\n");
                for (Donator donor : donators) {
                    reportContent.append(donor.getUserId()).append(",")
                            .append(donor.getFirstName()).append(",")
                            .append(donor.getLastName()).append(",")
                            .append(donor.getEmail()).append(",")
                            .append(donor.getPhoneNumber()).append(",")
                            .append(donor.getCharity() != null ? donor.getCharity().getCharity_name() : "None")
                            .append("\n");
                }
            }
            case "resources" -> {
                List<Resource> resources = fetchDataFromDatabase(Resource.class);
                reportContent.append("Id,Type,Quantity,Available\n");
                for (Resource r : resources) {
                    reportContent.append(r.getResource_id()).append(",")
                            .append(r.getType()).append(",")
                            .append(r.getQuantity()).append(",")
                            .append(r.isAvailable())
                            .append("\n");
                }
            }
            case "donations" -> {
                List<Donation> donations = fetchDataFromDatabase(Donation.class);
                reportContent.append("Donation Id,Donor Name,Status,Donation Date,Accept Date,Charity,Resources\n");
                for (Donation d : donations) {
                    String donatorName = d.getDonator().getFirstName() + " " + d.getDonator().getLastName();
                    String charityName = d.getDonator().getCharity() != null ? d.getDonator().getCharity().getCharity_name() : "None";
                    String resources = d.getResources().stream()
                            .map(r -> String.format("%s (x%d)", r.getType(), r.getQuantity()))
                            .collect(Collectors.joining("; "));

                    reportContent.append(d.getDonation_id()).append(",")
                            .append(donatorName).append(",")
                            .append(d.getStatus()).append(",")
                            .append(d.getDonationDate()).append(",")
                            .append(d.getAcceptDate() != null ? d.getAcceptDate() : "None").append(",")
                            .append(charityName).append(",")
                            .append(resources)
                            .append("\n");
                }
            }
            case "charities" -> {
                List<Charity> charities = fetchDataFromDatabase(Charity.class);
                reportContent.append("Id,Name,Description\n");
                for (Charity charity : charities) {
                    reportContent.append(charity.getCharity_id()).append(",")
                            .append(charity.getCharity_name()).append(",")
                            .append(charity.getCharity_description())
                            .append("\n");
                }
            }
            case "authorities" -> {
                List<Authority> authorities = fetchDataFromDatabase(Authority.class);
                reportContent.append("Id,Name,LastName,Region,PhoneNumber,Code\n");
                for (Authority authority : authorities) {
                    reportContent.append(authority.getAuthorityId()).append(",")
                            .append(authority.getName()).append(",")
                            .append(authority.getLastName()).append(",")
                            .append(authority.getRegion()).append(",")
                            .append(authority.getPhoneNumber()).append(",")
                            .append(authority.getCode())
                            .append("\n");
                }
            }
            case "reports" -> {
                List<Report> reports = fetchDataFromDatabase(Report.class);
                reportContent.append("Report Id,Category,Victim Name,Charity,Status,Creation Date,Accept Date,Completion Date,Resources,Volunteers\n");
                for (Report r : reports) {
                    String victimName = r.getVictim() != null ? r.getVictim().getFirstName() + " " + r.getVictim().getLastName() : "None";
                    String charityName = r.getCharity() != null ? r.getCharity().getCharity_name() : "None";
                    String status = r.getStatus() != null ? r.getStatus().name() : "None";
                    String resources = !r.getResourcesList().isEmpty()
                            ? r.getResourcesList().stream()
                            .map(resource -> resource.getType() + " (x" + resource.getQuantity() + ")")
                            .collect(Collectors.joining("; "))
                            : "None";
                    String volunteers = !r.getVolunteersList().isEmpty()
                            ? r.getVolunteersList().stream()
                            .map(volunteer -> volunteer.getFirstName() + " " + volunteer.getLastName())
                            .collect(Collectors.joining("; "))
                            : "None";

                    reportContent.append(r.getReport_id()).append(",")
                            .append(r.getCategory() != null ? r.getCategory() : "None").append(",")
                            .append(victimName).append(",")
                            .append(charityName).append(",")
                            .append(status).append(",")
                            .append(r.getReport_date() != null ? r.getReport_date() : "None").append(",")
                            .append(r.getAccept_date() != null ? r.getAccept_date() : "None").append(",")
                            .append(r.getCompletion_date() != null ? r.getCompletion_date() : "None").append(",")
                            .append(resources).append(",")
                            .append(volunteers)
                            .append("\n");
                }
            }
            case "volunteerevaluations", "volunteer evaluations", "evaluations" -> {
                List<VolunteerEvaluation> evaluations = fetchDataFromDatabase(VolunteerEvaluation.class);
                reportContent.append("Evaluation Id,Volunteer,Report,Rating,Description,Date\n");
                for (VolunteerEvaluation evaluation : evaluations) {
                    reportContent.append(evaluation.getEvaluationId()).append(",")
                            .append(evaluation.getVolunteer().getFirstName()).append(" ").append(evaluation.getVolunteer().getLastName()).append(",")
                            .append(evaluation.getReport() != null ? evaluation.getReport().getReport_id() : "None").append(",")
                            .append(evaluation.getRating()).append(",")
                            .append(evaluation.getDescription()).append(",")
                            .append(evaluation.getEvaluationDate())
                            .append("\n");
                }
            }
            default -> reportContent.append("Unknown report type.");
        }

        return reportContent.toString();
    }

    public String generateReportToPDF(String type) {
        String csvContent = generateReportToCSV(type);
        return csvContent.replace(",", " | ");
    }

    public String generateReportToJSON(String type) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String jsonContent = "";

        Map<String, Object> reportMap = new HashMap<>();

        switch (type.toLowerCase()) {
            case "volunteers" -> reportMap.put("volunteers", fetchDataFromDatabase(Volunteer.class).stream().map(v -> {
                v.setEvaluations(v.getEvaluations() == null ? new ArrayList<>() : Collections.emptyList());
                return v;
            }).collect(Collectors.toList()));
            case "victims" -> reportMap.put("victims", fetchDataFromDatabase(Victim.class));
            case "resources" -> reportMap.put("resources", fetchDataFromDatabase(Resource.class));
            case "donations" -> reportMap.put("donations", fetchDataFromDatabase(Donation.class));
            case "donators" -> reportMap.put("donators", fetchDataFromDatabase(Donator.class));
            case "charities" -> reportMap.put("charities", fetchDataFromDatabase(Charity.class));
            case "authorities" -> reportMap.put("authorities", fetchDataFromDatabase(Authority.class));
            case "reports" -> reportMap.put("reports", fetchDataFromDatabase(Report.class));
            case "volunteerevaluations", "volunteer evaluations", "evaluations" ->
                    reportMap.put("evaluations", fetchDataFromDatabase(VolunteerEvaluation.class).stream().peek(e -> e.getVolunteer().setEvaluations(null)).collect(Collectors.toList()));
            default -> reportMap.put("error", "Unknown report type.");
        }

        try {
            jsonContent = objectMapper.writeValueAsString(reportMap);
        } catch (Exception e) {
            e.printStackTrace();
            jsonContent = "{\"error\": \"Failed to generate JSON.\"}";
        }

        return jsonContent;
    }

    public void exportReport(String reportContent, String title, String format) {
        try {
            String fileName = title.replace(" ", "_") + "." + format.toLowerCase();
            switch (format.toLowerCase()) {
                case "pdf" -> {
                    PdfWriter writer = new PdfWriter(fileName);
                    PdfDocument pdfDocument = new PdfDocument(writer);

                    Document document = new Document(pdfDocument);

                    String fontPath = "src/main/resources/fonts/DejaVuSans.ttf";
                    PdfFont font = PdfFontFactory.createFont(
                            fontPath, PdfEncodings.IDENTITY_H);
                    document.setFont(font);

                    document.add(new Paragraph(title)
                            .setFont(font)
                            .setBold()
                            .setFontSize(16)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setMarginBottom(20));

                    List<String[]> rows = reportContent.lines()
                            .map(line -> line.split("\\|"))
                            .toList();

                    if (!rows.isEmpty()) {
                        float[] columnWidths = new float[rows.get(0).length];
                        for (int i = 0; i < columnWidths.length; i++) {
                            columnWidths[i] = 1;
                        }

                        Table table = new Table(UnitValue.createPercentArray(columnWidths));
                        table.setWidth(UnitValue.createPercentValue(100));

                        for (String header : rows.get(0)) {
                            table.addHeaderCell(new Cell()
                                    .add(new Paragraph(header.trim())
                                            .setFont(font)
                                            .setFontSize(10))
                                    .setFont(font)
                                    .setBold()
                                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setPadding(5)
                                    .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
                        }

                        for (int i = 1; i < rows.size(); i++) {
                            for (String cell : rows.get(i)) {
                                table.addCell(new Cell()
                                        .add(new Paragraph(cell.trim())
                                                .setFont(font)
                                                .setFontSize(8))
                                        .setTextAlignment(TextAlignment.LEFT)
                                        .setPadding(5)
                                        .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.TOP));
                            }
                        }

                        document.add(table);
                    }

                    document.close();

                }
                case "json" -> {
                    try (FileWriter fileWriter = new FileWriter(fileName);
                         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                        bufferedWriter.write(reportContent);
                    }
                }
                case "csv" -> {
                    try (BufferedWriter bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
                        bufferedWriter.write("\uFEFF");
                        bufferedWriter.write(reportContent);
                    }
                }
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            }
        } catch (IOException e) {
            System.err.println("Error exporting report: " + e.getMessage());
        }
    }
}
