package RepositoryTest;

import Classes.Charity;
import db.CharityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class CharityCRUDTest {

    private CharityRepository repo;

    @Test
    public void addTest() {
        Charity charity = new Charity("zubr", "organizacja pomocowa od 1978 roku w Polsce");


        repo.add(charity);
        Charity returnedCharity = repo.get(charity.getCharity_id());

        Assertions.assertEquals(charity.getCharity_id(), returnedCharity.getCharity_id());
        Assertions.assertEquals(charity.getCharity_description(), returnedCharity.getCharity_description());
        Assertions.assertEquals(charity.getCharity_name(), returnedCharity.getCharity_name());
    }
}
