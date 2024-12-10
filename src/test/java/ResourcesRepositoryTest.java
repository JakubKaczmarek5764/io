import Classes.Resource;
import db.ResourcesRepository;
import Classes.resourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourcesRepositoryTest {
    private ResourcesRepository repo = new ResourcesRepository();

    @BeforeEach
    void cleanUp() {
        repo.getAll().forEach(resource -> repo.remove(resource.getResource_id()));
    }
    @Test
    public void addTest() {
        Resource r = new Resource(resourceType.DONATION, 1);
        repo.add(r);
        Resource returnedR = repo.get(r.getResource_id());
        assert r.getResource_id() == returnedR.getResource_id();
        assert r.getType() == returnedR.getType();
        assert r.getQuantity() == returnedR.getQuantity();
    }
    @Test
    public void removeTest() {
        Resource r = new Resource(resourceType.DONATION, 1);
        repo.add(r);
        Resource returnedR = repo.get(r.getResource_id());
        assertEquals(r.getResource_id(),returnedR.getResource_id());
        repo.remove(r.getResource_id());
        assert repo.get(r.getResource_id()) == null;

    }
    @Test
    public void updateTest() {
        Resource r = new Resource(resourceType.DONATION, 1);
        repo.add(r);
        Resource returnedR = repo.get(r.getResource_id());
        assertEquals(r.getResource_id(),returnedR.getResource_id());
        r.setQuantity(2);
        repo.update(r);
        assertEquals(2,repo.get(r.getResource_id()).getQuantity());
    }
    @Test
    public void getTest() {

        Resource r = new Resource(resourceType.DONATION, 1);
        repo.add(r);
        Resource returnedR = repo.get(r.getResource_id());
        assertEquals(r.getResource_id(),returnedR.getResource_id());
        assertEquals(r.getType(),returnedR.getType());
        assertEquals(r.getQuantity(),returnedR.getQuantity());
    }
    @Test
    public void getAllTest() {
        Resource r = new Resource(resourceType.DONATION, 1);
        Resource r2 = new Resource(resourceType.DONATION, 2);
        repo.add(r);
        repo.add(r2);
        Resource returnedR = repo.get(r.getResource_id());
        assertEquals(r.getResource_id(),returnedR.getResource_id());
        assertEquals(r.getType(),returnedR.getType());
        assertEquals(r.getQuantity(),returnedR.getQuantity());
        assertEquals(2,repo.getAll().size());
    }
}
