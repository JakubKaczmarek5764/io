import Classes.*;
import db.ResourcesRepository;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//@SpringBootTest
public class VolunteerCRUDTest {

    private UsersRepository repo;
    private VolunteerService volunteerService;
    private ResourcesRepository resourcesRepository;
    private User user;


    @BeforeEach
    public void setUp() {
        user = new Volunteer("Paweł", "Pietrzak");
        repo = new UsersRepository();
        volunteerService = new VolunteerService();
        resourcesRepository = new ResourcesRepository();

    }
    @AfterEach
    void cleanUp() {
        repo.getAll().forEach(user -> repo.remove(user.getUserId()));

    }

    @Test
    public void addTest() {
        repo.add(user);
        User returnedUser = repo.get(user.getUserId());

        assertEquals(user.getUserId(), returnedUser.getUserId());
        assertEquals(user.getFirstName(), returnedUser.getFirstName());
        assertEquals(user.getLastName(), returnedUser.getLastName());
    }

    @Test
    public void removeTest() {
        repo.add(user);
        repo.remove(user.getUserId());
        User returnedUser = repo.get(user.getUserId());
        assertNull(returnedUser);
    }

    @Test
    public void updateTest() {
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
        repo.add(user);
        User user2 = new Volunteer("Jan", "Kowalski");
        repo.add(user2);
        assertEquals(2, repo.getAll().size());
    }

    @Test
    public void testDeleteVolunteer() {
        Volunteer volunteer = new Volunteer("Jane","Doe");
        volunteerService.addVolunteer(volunteer);
        ResponseEntity<Void> response = volunteerService.deleteVolunteer(volunteer.getVolunteerId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(repo.get(volunteer.getVolunteerId()));
    }

}
