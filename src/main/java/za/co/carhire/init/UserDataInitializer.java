package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.repository.authentication.IUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class UserDataInitializer implements CommandLineRunner {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.database.init.users.enabled:true}")
    private boolean initUsersEnabled;

    @Value("${app.database.init.users.clear-existing:false}")
    private boolean clearExistingUsers;

    @Value("${app.database.init.users.create-samples:false}")
    private boolean createSampleUsers;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createAdminUserIfNeeded();

        if (!initUsersEnabled) {
            System.out.println("User data initialization is disabled. Set app.database.init.users.enabled=true to enable sample users.");
            return;
        }

        List<User> existingUsers = userRepository.findAll();
        long existingCustomers = existingUsers.stream()
                .filter(u -> u.getRole() == Role.CUSTOMER)
                .count();

        if (existingCustomers > 0 && !clearExistingUsers) {
            System.out.println("Database already contains " + existingCustomers + " customer users.");
            System.out.println("Skipping user initialization. Set app.database.init.users.clear-existing=true to override.");
            return;
        }

        if (clearExistingUsers && existingCustomers > 0) {
            System.out.println("Clearing existing customer users as requested...");
            existingUsers.stream()
                    .filter(u -> u.getRole() == Role.CUSTOMER)
                    .forEach(u -> userRepository.delete(u));
            System.out.println("Existing customer users cleared.");
        }

        if (createSampleUsers) {
            System.out.println("============================================");
            System.out.println("Initializing sample user data...");
            System.out.println("============================================");

            createSampleCustomers();

            System.out.println("============================================");
            System.out.println("User initialization completed successfully!");
            System.out.println("--------------------------------------------");
            displayUserSummary();
            System.out.println("============================================");
        }
    }

    private void createAdminUserIfNeeded() {
        User existingAdmin = userRepository.findByEmail("admin@lgscarhire.com");

        if (existingAdmin != null) {
            System.out.println("✓ Admin user already exists.");
            return;
        }

        User admin = new User.Builder()
                .setIdNumber(9999999999L)
                .setName("System Administrator")
                .setEmail("admin@lgscarhire.com")
                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("+27 11 123 4567")
                .setPassword(passwordEncoder.encode("Admin@123"))
                .setLicenseNumber("ADMIN-001")
                .setRole(Role.ADMIN)
                .build();

        userRepository.save(admin);

        System.out.println("============================================");
        System.out.println("✓ Admin user created successfully!");
        System.out.println("  Email: admin@lgscarhire.com");
        System.out.println("  Password: Admin@123");
        System.out.println("  Role: ADMIN");
        System.out.println("============================================");
    }

    private void createSampleCustomers() {
        List<User> sampleUsers = new ArrayList<>();

        sampleUsers.add(new User.Builder()
                .setIdNumber(8501156789012L)
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .setDateOfBirth(LocalDate.of(1985, 1, 15))
                .setPhoneNumber("+27 82 123 4567")
                .setPassword(passwordEncoder.encode("Customer@123"))
                .setLicenseNumber("JD-85011567")
                .setRole(Role.CUSTOMER)
                .build());

        sampleUsers.add(new User.Builder()
                .setIdNumber(9203227890123L)
                .setName("Jane Smith")
                .setEmail("jane.smith@example.com")
                .setDateOfBirth(LocalDate.of(1992, 3, 22))
                .setPhoneNumber("+27 83 234 5678")
                .setPassword(passwordEncoder.encode("Customer@123"))
                .setLicenseNumber("JS-92032278")
                .setRole(Role.CUSTOMER)
                .build());

        sampleUsers.add(new User.Builder()
                .setIdNumber(8807108901234L)
                .setName("Michael Johnson")
                .setEmail("michael.johnson@example.com")
                .setDateOfBirth(LocalDate.of(1988, 7, 10))
                .setPhoneNumber("+27 84 345 6789")
                .setPassword(passwordEncoder.encode("Customer@123"))
                .setLicenseNumber("MJ-88071089")
                .setRole(Role.CUSTOMER)
                .build());

        sampleUsers.add(new User.Builder()
                .setIdNumber(9511309012345L)
                .setName("Sarah Williams")
                .setEmail("sarah.williams@example.com")
                .setDateOfBirth(LocalDate.of(1995, 11, 30))
                .setPhoneNumber("+27 85 456 7890")
                .setPassword(passwordEncoder.encode("Customer@123"))
                .setLicenseNumber("SW-95113090")
                .setRole(Role.CUSTOMER)
                .build());

        sampleUsers.add(new User.Builder()
                .setIdNumber(8006251234567L)
                .setName("David Brown")
                .setEmail("david.brown@example.com")
                .setDateOfBirth(LocalDate.of(1980, 6, 25))
                .setPhoneNumber("+27 86 567 8901")
                .setPassword(passwordEncoder.encode("Customer@123"))
                .setLicenseNumber("DB-80062512")
                .setRole(Role.CUSTOMER)
                .build());

        System.out.println("Creating sample customer users...");
        for (User user : sampleUsers) {
            userRepository.save(user);
            System.out.println("✓ Created user: " + user.getName() + " (" + user.getEmail() + ")");
        }

        System.out.println("\n" + sampleUsers.size() + " sample customer users created.");
        System.out.println("All sample users have password: Customer@123");
    }

    private void displayUserSummary() {
        List<User> allUsers = userRepository.findAll();
        long adminCount = allUsers.stream().filter(u -> u.getRole() == Role.ADMIN).count();
        long customerCount = allUsers.stream().filter(u -> u.getRole() == Role.CUSTOMER).count();

        System.out.println("User Summary:");
        System.out.println("- Total Users: " + allUsers.size());
        System.out.println("- Administrators: " + adminCount);
        System.out.println("- Customers: " + customerCount);
        System.out.println();
        System.out.println("Test Credentials:");
        System.out.println("- Admin: admin@lgscarhire.com / Admin@123");
        System.out.println("- Customer: john.doe@example.com / Customer@123");
    }
}
