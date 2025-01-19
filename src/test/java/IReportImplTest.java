import Classes.*;
import SKPH.SkphApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.CharityRepository;
import db.ReportRepository;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
    private User user;
    private Report report;


    @BeforeEach
    public void setUp() {
        charity = new Charity("Caritas", "Pomoc dla bezdomnych");
        user = new Victim("Paweł", "Pietrzak", charity);
        report = new Report((Victim) user, "Zaginął pies", charity);
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

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/report")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Report> reports = objectMapper.readValue(jsonResponse, new TypeReference<List<Report>>() {});



    }
}
