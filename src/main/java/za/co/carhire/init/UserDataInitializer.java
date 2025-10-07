package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.service.authentication.UserService;

import java.time.LocalDate;

/**
 * User Data Initializer
 * Creates default admin and user accounts when the application starts
 */
@Component
@Order(1)
public class UserDataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Value("${app.init.create-default-users:true}")
    private boolean createDefaultUsers;

    @Override
    public void run(String... args) throws Exception {
        if (!createDefaultUsers) {
            System.out.println("Default user creation is disabled.");
            return;
        }

        // Check if default admin already exists
        boolean adminExists = userService.findAll().stream()
                .anyMatch(u -> u.getEmail().equals("admin@lgs-carhire.co.za"));

        if (!adminExists) {
            System.out.println("============================================");
            System.out.println("Creating default admin account...");
            System.out.println("============================================");

            User admin = new User.Builder()
                    .setIdNumber(9999999999L)
                    .setName("Admin User")
                    .setEmail("admin@lgs-carhire.co.za")
                    .setDateOfBirth(LocalDate.of(1990, 1, 1))
                    .setPhoneNumber("+27123456789")
                    .setPassword("Admin@123")
                    .setLicenseNumber("ADMIN001")
                    .setRole(UserRole.ADMIN)
                    .build();

            userService.save(admin);

            System.out.println("Default admin created successfully!");
            System.out.println("Email: admin@lgs-carhire.co.za");
            System.out.println("Password: Admin@123");
            System.out.println("--------------------------------------------");
        }

        // Check if default user already exists
        boolean userExists = userService.findAll().stream()
                .anyMatch(u -> u.getEmail().equals("user@lgs-carhire.co.za"));

        if (!userExists) {
            System.out.println("Creating default user account...");

            User user = new User.Builder()
                    .setIdNumber(1111111111L)
                    .setName("Test User")
                    .setEmail("user@lgs-carhire.co.za")
                    .setDateOfBirth(LocalDate.of(1995, 5, 15))
                    .setPhoneNumber("+27987654321")
                    .setPassword("User@123")
                    .setLicenseNumber("USER001")
                    .setRole(UserRole.USER)
                    .build();

            userService.save(user);

            System.out.println("Default user created successfully!");
            System.out.println("Email: user@lgs-carhire.co.za");
            System.out.println("Password: User@123");
            System.out.println("============================================");
        }

        if (adminExists && userExists) {
            System.out.println("Default users already exist. Skipping creation.");
        }
    }
}
