import Classes.*;
import SKPH.SkphApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.CharityRepository;
import db.DonationsRepository;
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

import java.sql.Date;
import java.util.List;

@SpringBootTest(classes = SkphApplication.class)
@AutoConfigureMockMvc
public class IDonationImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UsersRepository userRepo;
    private DonationsRepository donationsRepository;
    private CharityRepository charityRepo;
    private Charity charity;
    private Charity charity1;
    private User user;
    private User user1;
    private Donation donation;
    private Donation donation1;


    @BeforeEach
    public void setUp() {
        charity = new Charity("Pomoc","Pomagamy");
        charity1 = new Charity("CzerwonyKrzyz","Pomagamy zawsze");
        user = new Donator("Jan","Nowak",charity);
        user1 = new Donator("Adam","Nowak",charity1);
        donation = new Donation((Donator) user, donationStatus.pending, Date.valueOf("2023-12-08"),null);
        donation1 = new Donation((Donator) user1, donationStatus.pending, Date.valueOf("2024-12-08"),null);
        userRepo = new UsersRepository();
        donationsRepository = new DonationsRepository();
        charityRepo = new CharityRepository();


    }

    @AfterEach
    void cleanUp() {
        donationsRepository.getAll().forEach(donation -> donationsRepository.remove(donation.getDonation_id()));
        userRepo.getAll().forEach(user -> userRepo.remove(user.getUserId()));
        charityRepo.getAll().forEach(charity -> charityRepo.remove(charity.getCharity_id()));
    }

    @Test
    public void GetAllTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        donationsRepository.add(donation);

        charityRepo.add(charity1);
        userRepo.add(user1);
        donationsRepository.add(donation1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/donation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Donation> response = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("Jan", response.getFirst().getDonator().getFirstName());
        Assertions.assertEquals("Pomagamy", response.get(0).getDonator().getCharity().getCharity_description());
        Assertions.assertEquals(donationStatus.pending,response.get(0).getStatus());
        Assertions.assertEquals("Adam", response.get(1).getDonator().getFirstName());
        Assertions.assertEquals("Pomagamy zawsze", response.get(1).getDonator().getCharity().getCharity_description());
        Assertions.assertEquals(donationStatus.pending,response.get(1).getStatus());
    }

    @Test
    public void GetDonationByIdTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        donationsRepository.add(donation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/donation/" + donation.getDonation_id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Donation response = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Jan", response.getDonator().getFirstName());
        Assertions.assertEquals("Pomagamy", response.getDonator().getCharity().getCharity_description());
        Assertions.assertEquals(donationStatus.pending,response.getStatus());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get("/donation/-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse1.isEmpty());
    }

    @Test
    public void getDonationByCharityIdTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        donationsRepository.add(donation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/donation/charity/" + charity.getCharity_id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Donation> response = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Jan", response.getFirst().getDonator().getFirstName());
        Assertions.assertEquals("Pomagamy", response.getFirst().getDonator().getCharity().getCharity_description());
        Assertions.assertEquals(donationStatus.pending,response.getFirst().getStatus());
    }

    @Test
    public void getDonationByDonatorIdTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        donationsRepository.add(donation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/donation/donator/" + user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Donation> response = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Jan", response.getFirst().getDonator().getFirstName());
        Assertions.assertEquals("Pomagamy", response.getFirst().getDonator().getCharity().getCharity_description());
        Assertions.assertEquals(donationStatus.pending,response.getFirst().getStatus());
    }

    @Test
    public void updateDonationTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        donationsRepository.add(donation);

        Assertions.assertEquals(Date.valueOf("2023-12-08"),donation.getDonationDate());
        Assertions.assertNull(donation.getAcceptDate());
        Assertions.assertEquals(donationStatus.pending,donation.getStatus());

        donation.setDonationDate(Date.valueOf("2023-12-18"));
        donation.setAcceptDate(Date.valueOf("2023-12-19"));
        donation.setStatus(donationStatus.accepted);

        mockMvc.perform(MockMvcRequestBuilders.put("/donation/" + donation.getDonation_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(donation)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(Date.valueOf("2023-12-18"),donation.getDonationDate());
        Assertions.assertEquals(Date.valueOf("2023-12-19"),donation.getAcceptDate());
        Assertions.assertEquals(donationStatus.accepted,donation.getStatus());
    }

    @Test
    public void DeleteDonationTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);
        donationsRepository.add(donation);

        Assertions.assertEquals(1, donationsRepository.getAll().size());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/donation/-1" )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();

        Assertions.assertTrue(jsonResponse.isEmpty());
        Assertions.assertEquals(1, donationsRepository.getAll().size());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.delete("/donation/" + donation.getDonation_id() )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse1 = result1.getResponse().getContentAsString();
        Donation response = objectMapper.readValue(jsonResponse1, new TypeReference<>() {});

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Jan", response.getDonator().getFirstName());
        Assertions.assertEquals("Pomagamy", response.getDonator().getCharity().getCharity_description());
        Assertions.assertEquals(donationStatus.pending,response.getStatus());
        Assertions.assertEquals(0, donationsRepository.getAll().size());
    }

    @Test
    public void createDonationTest() throws Exception {
        charityRepo.add(charity);
        userRepo.add(user);

        Assertions.assertEquals(0, donationsRepository.getAll().size());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(donation)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Donation response = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        Donation don = donationsRepository.getAll().getFirst();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(don.getAcceptDate(), response.getAcceptDate());
        Assertions.assertEquals(don.getDonationDate().toString(), response.getDonationDate().toString());
        Assertions.assertEquals(don.getStatus(), response.getStatus());

    }

}
