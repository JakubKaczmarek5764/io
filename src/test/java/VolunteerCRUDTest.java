import Classes.Charity;
import Classes.User;
import Classes.Victim;
import Classes.Volunteer;
import db.CharityRepository;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class VolunteerCRUDTest {

    private UsersRepository repo;
    private User user;


    @BeforeEach
    public void setUp() {
        user = new Volunteer("Paweł", "Pietrzak");
        repo = new UsersRepository();

    }
    @AfterEach
    void cleanUp() {
        repo.getAll().forEach(user -> repo.remove(user.getUserId()));

    }

    @Test
    public void addTest() {
        repo.add(user);
        User returnedUser = repo.get(user.getUserId());

        Assertions.assertEquals(user.getUserId(), returnedUser.getUserId());
        Assertions.assertEquals(user.getFirstName(), returnedUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), returnedUser.getLastName());
    }

    @Test
    public void removeTest() {
        repo.add(user);
        repo.remove(user.getUserId());
        User returnedUser = repo.get(user.getUserId());
        Assertions.assertNull(returnedUser);
    }

    @Test
    public void updateTest() {
        repo.add(user);
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        repo.update(user);
        User returnedUser = repo.get(user.getUserId());
        Assertions.assertEquals(user.getUserId(), returnedUser.getUserId());
        Assertions.assertEquals(user.getFirstName(), returnedUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), returnedUser.getLastName());
    }

    @Test
    public void getAllTest() {
        repo.add(user);
        User user2 = new Volunteer("Jan", "Kowalski");
        repo.add(user2);
        Assertions.assertEquals(2, repo.getAll().size());
    }

}
