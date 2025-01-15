import Classes.Authority;
import Classes.Charity;
import db.AuthoritiesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthoritiesCRUDTest {

    private AuthoritiesRepository repo;
    private Authority authority;

    @BeforeEach
    public void setUp() {
        authority = new Authority("tomasz", "Kot", "1234", "malopolskie", "123-456", "login", "haslo");
        repo = new AuthoritiesRepository();
    }

    @AfterEach
    void cleanUp() {
        repo.getAll().forEach(authority -> repo.remove(authority.getAuthorityId()));
    }

    @Test
    public void testAdd() {
        repo.add(authority);
        Authority returnedAuthority = repo.get(authority.getAuthorityId());

        Assertions.assertEquals(authority.getAuthorityId(), returnedAuthority.getAuthorityId());
        Assertions.assertEquals(authority.getName(), returnedAuthority.getName());
        Assertions.assertEquals(authority.getCode(), returnedAuthority.getCode());
    }

    @Test
    public void testUpdate() {
        repo.add(authority);
        Authority returnedAuthority = repo.get(authority.getAuthorityId());
        returnedAuthority.setName("Jan");
        returnedAuthority.setCode("4321");
        repo.update(returnedAuthority);
        Authority updatedAuthority = repo.get(returnedAuthority.getAuthorityId());
        Assertions.assertTrue(updatedAuthority.getName().equals("Jan"));
        Assertions.assertTrue(updatedAuthority.getCode().equals("4321"));
        Assertions.assertFalse(updatedAuthority.getName().equals(authority.getName()));
    }

    @Test
    public void testRemove() {
        repo.add(authority);
        repo.remove(authority.getAuthorityId());
        Authority returnedAuthority = repo.get(authority.getAuthorityId());
        Assertions.assertNull(returnedAuthority);
    }

}