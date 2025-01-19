import Classes.*;
import db.CharityRepository;
import db.ResourcesRepository;
import db.TaskRepository;
import db.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Classes.status.IN_PROGRESS;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class TaskCRUDTest {

    private UsersRepository repo;
    private User user;
    private Task task;
    private TaskRepository taskRepo;
    private ResourcesRepository resourcesRepository;


    @BeforeEach
    public void setUp() {
        user = new Volunteer("Paweł", "Pietrzak");
        repo = new UsersRepository();
        taskRepo = new TaskRepository();
        resourcesRepository = new ResourcesRepository();
        task = new Task("Zadanie testowe",IN_PROGRESS);

    }
    @AfterEach
    void cleanUp() {

        taskRepo.getAll().forEach(task -> taskRepo.remove(task.getTaskId()));
        repo.getAll().forEach(user -> repo.remove(user.getUserId()));
    }

    @Test
    public void addTest() {
        repo.add(user);
        taskRepo.add(task);
        task.setVolunteer((Volunteer) user);
        taskRepo.update(task);
        Task returnedTask = taskRepo.get(task.getTaskId());

        Assertions.assertEquals(task.getTaskId(), returnedTask.getTaskId());
        Assertions.assertEquals(task.getVolunteer().getFirstName(), returnedTask.getVolunteer().getFirstName());
        Assertions.assertEquals(task.getDescription(), returnedTask.getDescription());
    }

    @Test
    public void removeTest() {
        repo.add(user);
        taskRepo.add(task);
        task.setVolunteer((Volunteer) user);
        taskRepo.update(task);
        taskRepo.remove(task.getTaskId());
        Task returnedTask = taskRepo.get(task.getTaskId());
        Assertions.assertNull(returnedTask);
    }

    @Test
    public void updateTest() {
        repo.add(user);
        taskRepo.add(task);
        task.setVolunteer((Volunteer) user);
        task.setDescription("Nowy opis");
        taskRepo.update(task);
        Task returnedTask = taskRepo.get(task.getTaskId());
        Assertions.assertEquals(task.getTaskId(), returnedTask.getTaskId());
        Assertions.assertEquals(task.getDescription(), returnedTask.getDescription());
    }

    @Test
    public void getAllTest() {
        repo.add(user);
        taskRepo.add(task);
        task.setVolunteer((Volunteer) user);
        taskRepo.update(task);
        Task task2 = new Task("praca domowa", IN_PROGRESS);
        taskRepo.add(task2);
        task2.setVolunteer((Volunteer) user);
        taskRepo.update(task);
        Assertions.assertEquals(2, taskRepo.getAll().size());

    }

}
