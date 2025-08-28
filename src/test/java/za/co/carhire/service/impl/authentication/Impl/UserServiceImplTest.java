package za.co.carhire.service.authentication.Impl;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.factory.authentication.UserFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    private static UserServiceImpl service;
    private static User user = UserFactory.createUser("9207891234567", "John Doe", "john@gmail.com", "1992-01-01",
            "0821234567", "password123", "LIC1001");

    @Test
    @Order(1)
    void save() {
        User saved = service.save(user);
        assertNotNull(saved);
        System.out.println("Saved: " + saved);
    }

    @Test
    @Order(2)
    void read() {
        User read = service.read(user.getUserId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void update() {
        User updated = new User.Builder()
                .copy(user)
                .setEmail("updatedemail@gmail.com")
                .setPhoneNumber("0834567890")
                .build();

        User result = service.update(updated);
        assertNotNull(result);
        assertEquals("updatedemail@gmail.com", result.getEmail());
        System.out.println("Updated User: " + result);
    }

    @Test
    @Order(4)
    void findUserByIdNumber() {
        List<User> found = service.findByIdNumber(user.getIdNumber());
        assertFalse(found.isEmpty());
        System.out.println("Found by ID number: " + found);
    }

    @Test
    @Order(5)
    void findAll() {
        List<User> all = service.findAll();
        assertFalse(all.isEmpty());
        System.out.println("All users: " + all);
    }

    @Test
    @Order(6)
    void delete() {
        service.delete(user.getUserId());
        assertNull(service.read(user.getUserId()));
        System.out.println("Deleted user ID: " + user.getUserId());
    }
}