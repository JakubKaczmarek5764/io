import Classes.Charity;
import db.CharityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class CharityCRUDTest {

    private CharityRepository repo;
    private Charity charity;
    @BeforeEach
    public void setUp() {
        charity = new Charity("zubr", "organizacja pomocowa od 1978 roku w Polsce");
        repo = new CharityRepository();
    }
    @AfterEach
    void cleanUp() {
        repo.getAll().forEach(charity -> repo.remove(charity.getCharity_id()));
    }

    @Test
    public void addTest() {

        repo.add(charity);
        Charity returnedCharity = repo.get(charity.getCharity_id());

        Assertions.assertEquals(charity.getCharity_id(), returnedCharity.getCharity_id());
        Assertions.assertEquals(charity.getCharity_description(), returnedCharity.getCharity_description());
        Assertions.assertEquals(charity.getCharity_name(), returnedCharity.getCharity_name());
    }

    @Test
    public void removeTest() {
        repo.add(charity);
        repo.remove(charity.getCharity_id());
        Charity returnedCharity = repo.get(charity.getCharity_id());
        Assertions.assertNull(returnedCharity);
    }

    @Test
    public void updateTest() {
        repo.add(charity);
        charity.setCharity_name("zubr2");
        charity.setCharity_description("organizacja pomocowa od 1978 roku w Polsce");
        repo.update(charity);
        Charity returnedCharity = repo.get(charity.getCharity_id());
        Assertions.assertEquals(charity.getCharity_id(), returnedCharity.getCharity_id());
        Assertions.assertEquals(charity.getCharity_description(), returnedCharity.getCharity_description());
        Assertions.assertEquals(charity.getCharity_name(), returnedCharity.getCharity_name());
    }

    @Test
    public void getAllTest() {
        repo.add(charity);
        Charity charity2 = new Charity("zubr2", "organizacja pomocowa od 1978 roku w Polsce");
        repo.add(charity2);
        Assertions.assertEquals(2, repo.getAll().size());
    }

}
