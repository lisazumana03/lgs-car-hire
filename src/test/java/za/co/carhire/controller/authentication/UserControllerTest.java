package za.co.carhire.controller.authentication;
/* UserControllerTest.java

     UserControllerTest/authentication/factory class Test

     Author: Bonga Velem

     Student Number: 220052379

     */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.service.authentication.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userService;

    private Long uniqueIdNumber;

    @BeforeEach
    void setUp() {
        uniqueIdNumber = System.currentTimeMillis() % 1000000000L;
    }

    @Test
    void testCreateUser() {
        User user = new User.Builder()
                .setName("John Doe")
                .setEmail("john@example.com")
                .setIdNumber(uniqueIdNumber)
                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("1234567890")
                .setPassword("password123")
                .setLicenseNumber("LIC" + uniqueIdNumber)
                .build();

        User saved = userService.save(user);

        assertNotNull(saved);
        assertNotNull(saved.getUserId());
        assertEquals("John Doe", saved.getName());
        assertEquals("john@example.com", saved.getEmail());
        assertEquals(uniqueIdNumber, saved.getIdNumber());
        assertEquals("1234567890", saved.getPhoneNumber());
        assertEquals("password123", saved.getPassword());
        assertEquals("LIC" + uniqueIdNumber, saved.getLicenseNumber());
    }

    @Test
    void testReadUser() {
        User user = new User.Builder()
                .setName("Jane Smith")
                .setEmail("jane@example.com")
                .setIdNumber(uniqueIdNumber + 1)
                .setDateOfBirth(LocalDate.of(1995, 5, 15))
                .setPhoneNumber("0987654321")
                .setPassword("password456")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 1))
                .build();

        User saved = userService.save(user);
        Integer userId = saved.getUserId();

        User found = userService.read(userId);

        assertNotNull(found);
        assertEquals(userId, found.getUserId());
        assertEquals("Jane Smith", found.getName());
        assertEquals("jane@example.com", found.getEmail());
        assertEquals(uniqueIdNumber + 1, found.getIdNumber());
    }

    @Test
    void testUpdateUser() {
        User user = new User.Builder()
                .setName("Original Name")
                .setEmail("original@example.com")
                .setIdNumber(uniqueIdNumber + 2)
                .setDateOfBirth(LocalDate.of(1985, 10, 20))
                .setPhoneNumber("1112223333")
                .setPassword("password789")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 2))
                .build();

        User saved = userService.save(user);
        Integer userId = saved.getUserId();

        User updatedUser = new User.Builder()
                .setUserId(userId)
                .setName("Updated Name")
                .setEmail("updated@example.com")
                .setIdNumber(uniqueIdNumber + 2)
                .setDateOfBirth(LocalDate.of(1985, 10, 20))
                .setPhoneNumber("9998887777")
                .setPassword("newpassword")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 2))
                .build();

        User updated = userService.save(updatedUser);

        assertNotNull(updated);
        assertEquals(userId, updated.getUserId());
        assertEquals("Updated Name", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
        assertEquals("9998887777", updated.getPhoneNumber());
        assertEquals("newpassword", updated.getPassword());
    }

    @Test
    void testDeleteUser() {
        User user = new User.Builder()
                .setName("Delete Test")
                .setEmail("delete@example.com")
                .setIdNumber(uniqueIdNumber + 3)
                .setDateOfBirth(LocalDate.of(1980, 3, 15))
                .setPhoneNumber("5556667777")
                .setPassword("password999")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 3))
                .build();

        User saved = userService.save(user);
        Integer userId = saved.getUserId();

        assertNotNull(userService.read(userId));

        userService.delete(userId);

        assertNull(userService.read(userId));
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User.Builder()
                .setName("First User")
                .setEmail("first@example.com")
                .setIdNumber(uniqueIdNumber + 4)
                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("1111111111")
                .setPassword("password111")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 4))
                .build();

        User user2 = new User.Builder()
                .setName("Second User")
                .setEmail("second@example.com")
                .setIdNumber(uniqueIdNumber + 5)
                .setDateOfBirth(LocalDate.of(1995, 5, 5))
                .setPhoneNumber("2222222222")
                .setPassword("password222")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 5))
                .build();

        userService.save(user1);
        userService.save(user2);

        List<User> allUsers = userService.findAll();

        assertNotNull(allUsers);
        assertTrue(allUsers.size() >= 2);
    }

    @Test
    void testUserNotFound() {
        User notFound = userService.read(99999);
        assertNull(notFound);
    }

    @Test
    void testCreateUserWithEmptyName() {
        User user = new User.Builder()
                .setName("")
                .setEmail("empty@example.com")
                .setIdNumber(uniqueIdNumber + 6)
                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("3333333333")
                .setPassword("password333")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 6))
                .build();

        User saved = userService.save(user);

        assertNotNull(saved);
        assertEquals("", saved.getName());
        assertEquals("empty@example.com", saved.getEmail());
    }

    @Test
    void testCreateUserWithInvalidEmail() {
        User user = new User.Builder()
                .setName("Invalid Email User")
                .setEmail("invalid-email-format")
                .setIdNumber(uniqueIdNumber + 7)
                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("4444444444")
                .setPassword("password444")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 7))
                .build();

        User saved = userService.save(user);

        assertNotNull(saved);
        assertEquals("Invalid Email User", saved.getName());
        assertEquals("invalid-email-format", saved.getEmail());
    }

    @Test
    void testUserBuilderPattern() {
        User user = new User.Builder()
                .setName("Builder Test")
                .setEmail("builder@example.com")
                .setIdNumber(uniqueIdNumber + 8)
                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("5555555555")
                .setPassword("password555")
                .setLicenseNumber("LIC" + (uniqueIdNumber + 8))
                .build();

        assertNotNull(user);
        assertEquals("Builder Test", user.getName());
        assertEquals("builder@example.com", user.getEmail());
        assertEquals(uniqueIdNumber + 8, user.getIdNumber());
        assertEquals(LocalDate.of(1990, 1, 1), user.getDateOfBirth());
        assertEquals("5555555555", user.getPhoneNumber());
        assertEquals("password555", user.getPassword());
        assertEquals("LIC" + (uniqueIdNumber + 8), user.getLicenseNumber());
    }
}