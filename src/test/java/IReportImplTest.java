import Classes.*;
import SKPH.SkphApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.CharityRepository;
import db.ReportRepository;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(classes = SkphApplication.class)
@AutoConfigureMockMvc
public class IReportImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UsersRepository userRepo;
    private ReportRepository reportRepo;
    private CharityRepository charityRepo;
    private Charity charity;
    private Charity charity2;
    private User user;
    private User user2;
    private Report report;
    private Report report2;


    @BeforeEach
    public void setUp() {
        charity = new Charity("Caritas", "Pomoc dla bezdomnych");
        charity2 = new Charity("Charity", "Opis");
        user = new Victim("Jeremiasz", "Rożowy", charity);
        user2 = new Victim("Włodzimierz", "Biały", charity2);
        report = new Report((Victim) user, "Zaginął pies", charity);
        report2 = new Report((Victim) user2, "Zaginął kot", charity2);
        userRepo = new UsersRepository();
        reportRepo = new ReportRepository();
        charityRepo = new CharityRepository();

    }
    @AfterEach
    void cleanUp() {
        reportRepo.getAll().forEach(report -> reportRepo.remove(report.getReport_id()));
        userRepo.getAll().forEach(user -> userRepo.remove(user.getUserId()));
        charityRepo.getAll().forEach(charity -> charityRepo.remove(charity.getCharity_id()));
    }


    @Test
    public void GetAllTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        reportRepo.add(report);

        charityRepo.add(charity2);
        userRepo.add(user2);
        reportRepo.add(report2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/report")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Report> reports = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(2, reports.size());
        Assertions.assertEquals(report.getCategory(), reports.getFirst().getCategory());
        Assertions.assertEquals("Jeremiasz", reports.get(0).getVictim().getFirstName());
        Assertions.assertEquals("Pomoc dla bezdomnych", reports.get(0).getCharity().getCharity_description());
        Assertions.assertEquals(report2.getCategory(), reports.get(1).getCategory());
        Assertions.assertEquals("Włodzimierz", reports.get(1).getVictim().getFirstName());
        Assertions.assertEquals("Opis", reports.get(1).getCharity().getCharity_description());
    }

    @Test
    public void GetByReportId() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        reportRepo.add(report);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/report/" + report.getReport_id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Report reportget = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertNotNull(reportget);
        Assertions.assertEquals(report.getCategory(), reportget.getCategory());
        Assertions.assertEquals("Jeremiasz", reportget.getVictim().getFirstName());
        Assertions.assertEquals("Pomoc dla bezdomnych", reportget.getCharity().getCharity_description());

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/report/-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        String jsonResponse2 = result2.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse2.isEmpty());
    }

    @Test
    public void GetByCharityId() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        reportRepo.add(report);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/report/charity/" + charity.getCharity_id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Report> reports = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(1, reports.size());
        Assertions.assertEquals(reports.getFirst().getCategory(), report.getCategory());
        Assertions.assertEquals("Jeremiasz", reports.getFirst().getVictim().getFirstName());
        Assertions.assertEquals("Pomoc dla bezdomnych", reports.getFirst().getCharity().getCharity_description());
    }

    @Test
    public void GetByUserId() throws Exception {
        charityRepo.add(charity2);
        userRepo.add(user2);
        reportRepo.add(report2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/report/victim/" + user2.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Report> reports = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(1, reports.size());
        Assertions.assertEquals(reports.getFirst().getCategory(), report2.getCategory());
        Assertions.assertEquals("Włodzimierz", reports.getFirst().getVictim().getFirstName());
        Assertions.assertEquals("Opis", reports.getFirst().getCharity().getCharity_description());
    }

    @Test
    public void AddReport() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);

        Assertions.assertEquals(0, reportRepo.getAll().size());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(report)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Report report = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Report reportDB = reportRepo.getAll().getFirst();

        Assertions.assertNotNull(report);
        Assertions.assertEquals(reportDB.getCategory(), report.getCategory());
        Assertions.assertEquals(reportDB.getVictim().getFirstName(), report.getVictim().getFirstName());
        Assertions.assertEquals(reportDB.getCharity().getCharity_description(), report.getCharity().getCharity_description());
    }

    @Test
    public void DeleteReport() throws Exception {
        charityRepo.add(charity2);
        userRepo.add(user2);
        reportRepo.add(report2);

        Assertions.assertEquals(1, reportRepo.getAll().size());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/report/-1" )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.isEmpty());
        Assertions.assertEquals(1, reportRepo.getAll().size());

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete("/report/" + report2.getReport_id() )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse2 = result2.getResponse().getContentAsString();
        Report report2delete = objectMapper.readValue(jsonResponse2, new TypeReference<>() {
        });

        Assertions.assertEquals(report2.getCategory(), report2delete.getCategory());
        Assertions.assertEquals("Włodzimierz", report2delete.getVictim().getFirstName());
        Assertions.assertEquals("Opis", report2delete.getCharity().getCharity_description());
        Assertions.assertEquals(0, reportRepo.getAll().size());
    }

    @Test
    public void UpdateReport() throws Exception {
        charityRepo.add(charity);
        charityRepo.add(charity2);
        userRepo.add(user);
        userRepo.add(user2);
        reportRepo.add(report2);

        Assertions.assertEquals("Zaginął kot", report2.getCategory());
        Assertions.assertEquals("Włodzimierz", report2.getVictim().getFirstName());
        Assertions.assertNull(report2.getStatus());

        report2.setStatus(reportStatus.accepted);
        report2.setCategory("Pożar");
        report2.setVictim((Victim) user);

        mockMvc.perform(MockMvcRequestBuilders.put("/report/" + report2.getReport_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report2)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Report report1 = reportRepo.get(report2.getReport_id());
        Assertions.assertEquals("Pożar", report1.getCategory());
        Assertions.assertEquals(reportStatus.accepted, report1.getStatus());
        Assertions.assertEquals("Jeremiasz", report1.getVictim().getFirstName());
    }
}
