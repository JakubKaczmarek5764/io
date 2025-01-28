import Classes.*;
import Resources.Volunteer;
import Resources.VolunteerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
public class VolunteerCRUDTest {

    private UsersRepository repo;
    private VolunteerService volunteerService;
    private MockMvc mockMvc;

    private Volunteer volunteer;


    @BeforeEach
    public void setUp() {
        volunteer = new Volunteer("Jakub", "Pietrzak", "hashedPassword", "jakub@example.com", "123456789", LocalDate.now());
        repo = new UsersRepository();
        repo.add(volunteer);
        volunteerService = new VolunteerService();
        mockMvc = MockMvcBuilders.standaloneSetup(volunteerService).build();

    }
    @AfterEach
    void cleanUp() {
        repo.getAll().forEach(user -> repo.remove(user.getUserId()));

    }

    @Test
    public void addTest() {
        Volunteer user = new Volunteer("Paweł", "Pietrzak", "hashedPassword", "pawel@example.com", "987654321", LocalDate.now());
        repo.add(user);
        User returnedUser = repo.get(user.getUserId());

        assertEquals(user.getUserId(), returnedUser.getUserId());
        assertEquals(user.getFirstName(), returnedUser.getFirstName());
        assertEquals(user.getLastName(), returnedUser.getLastName());
    }

    @Test
    public void removeTest() {
        Volunteer user = new Volunteer("Paweł", "Pietrzak", "hashedPassword", "pawel@example.com", "987654321", LocalDate.now());
        repo.add(user);
        repo.remove(user.getUserId());
        User returnedUser = repo.get(user.getUserId());
        assertNull(returnedUser);
    }

    @Test
    public void updateTest() {
        Volunteer user = new Volunteer("Paweł", "Pietrzak", "hashedPassword", "pawel@example.com", "987654321", LocalDate.now());
        repo.add(user);
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        repo.update(user);
        User returnedUser = repo.get(user.getUserId());
        assertEquals(user.getUserId(), returnedUser.getUserId());
        assertEquals(user.getFirstName(), returnedUser.getFirstName());
        assertEquals(user.getLastName(), returnedUser.getLastName());
    }

    @Test
    public void getAllTest() {
        Volunteer user = new Volunteer("Paweł", "Pietrzak", "hashedPassword", "pawel@example.com", "987654321", LocalDate.now());
        repo.add(user);
        Volunteer user2 = new Volunteer("Jan", "Kowalski", "hashedPassword", "jan@example.com", "111222333", LocalDate.now());
        repo.add(user2);
        assertEquals(3, repo.getAll().size());
    }

    @Test
    public void addVolunteerTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/volunteer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Janusz\", \"lastName\": \"Kowalski\", \"passwordHash\": " +
                                "\"hashedpassword123\", " +
                                "\"email\": " + "\"jan.kowalski@example.com\", \"phoneNumber\": \"123-456-789\", " +
                                "\"registrationDate\": \"2023-01-01\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response);
        Long volunteerId = responseJson.get("userId").asLong();;

        Volunteer updatedVolunteer = (Volunteer) repo.get(volunteerId);

        assertNotNull(updatedVolunteer, "Volunteer should be found in repository after saving");
        assertEquals("Janusz", updatedVolunteer.getFirstName());
        assertEquals("Kowalski", updatedVolunteer.getLastName());
    }

    @Test
    public void testDeleteVolunteer() {
        Volunteer volunteer = new Volunteer("Jane", "Doe", "hashedPassword",
                "jane@example.com", "123123123", LocalDate.now());
        volunteerService.addVolunteer(volunteer);
        ResponseEntity<Void> response = volunteerService.deleteVolunteer(volunteer.getVolunteerId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(repo.get(volunteer.getVolunteerId()));
    }

    @Test
    public void updateVolunteerTest() throws Exception {
        volunteer.setFirstName("Jan");
        volunteer.setLastName("Kowalski");

        mockMvc.perform(MockMvcRequestBuilders.put("/volunteer/update/{volunteerId}", volunteer.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Jan\", \"lastName\": \"Kowalski\", \"email\": " +
                                "\"jan.kowalski@example.com\", \"phoneNumber\": \"123-456-789\", " +
                                "\"registrationDate\": \"2023-01-01\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Volunteer updatedVolunteer = (Volunteer) repo.get(volunteer.getUserId());
        assertEquals("Jan", updatedVolunteer.getFirstName());
        assertEquals("Kowalski", updatedVolunteer.getLastName());
    }

}
