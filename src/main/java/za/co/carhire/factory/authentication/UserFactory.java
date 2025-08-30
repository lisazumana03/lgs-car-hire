package za.co.carhire.factory.authentication;

import za.co.carhire.util.Helper;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import java.time.LocalDate;

public class UserFactory {

    public static User createCustomer(String idNumber, String name, String email,
                                      String dateOfBirth, String phoneNumber,
                                      String password, String licenseNumber) {
        return createUser(idNumber, name, email, dateOfBirth, phoneNumber,
                password, licenseNumber, UserRole.CUSTOMER);
    }

    public static User createAdmin(String idNumber, String name, String email,
                                   String dateOfBirth, String phoneNumber,
                                   String password) {
        return createUser(idNumber, name, email, dateOfBirth, phoneNumber,
                password, null, UserRole.ADMIN);
    }

    public static User createUser(String idNumber, String name, String email,
                                  String dateOfBirth, String phoneNumber,
                                  String password, String licenseNumber, UserRole role) {
        if (Helper.isEmptyOrNull(name) ||
                !Helper.isValidNationalIDNumber(idNumber) ||
                !Helper.isValidEmail(email) ||
                !Helper.isValidDate(dateOfBirth) ||
                Helper.isEmptyOrNull(phoneNumber) ||
                Helper.isEmptyOrNull(password)) {
            return null;
        }

        if (role == UserRole.CUSTOMER && Helper.isEmptyOrNull(licenseNumber)) {
            return null;
        }

        return new User.Builder()
                .setName(name)
                .setIdNumber(Long.valueOf(idNumber))
                .setEmail(email)
                .setDateOfBirth(LocalDate.parse(dateOfBirth))
                .setPhoneNumber(phoneNumber)
                .setPassword(password)
                .setLicenseNumber(licenseNumber)
                .setRole(role != null ? role : UserRole.CUSTOMER)
                .build();
    }

    public static User createUser(String idNumber, String name, String email,
                                  String dateOfBirth, String phoneNumber,
                                  String password, String licenseNumber) {
        return createUser(idNumber, name, email, dateOfBirth, phoneNumber,
                password, licenseNumber, UserRole.CUSTOMER);
    }

    public static User loginUser(String email, String password) {
        if (!Helper.isValidEmail(email) || Helper.isEmptyOrNull(password)) {
            return null;
        }

        return new User.Builder()
                .setEmail(email)
                .setPassword(password)
                .build();
    }

    public static User createTestCustomer() {
        return new User.Builder()
                .setUserId(1)
                .setIdNumber(9001015800084L)
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                .setPhoneNumber("0821234567")
                .setPassword("password123")
                .setLicenseNumber("L123456")
                .setRole(UserRole.CUSTOMER)
                .build();
    }

    public static User createTestAdmin() {
        return new User.Builder()
                .setUserId(2)
                .setIdNumber(8505065800084L)
                .setName("Jane Admin")
                .setEmail("admin@carhire.co.za")
                .setDateOfBirth(LocalDate.of(1985, 5, 6))
                .setPhoneNumber("0829876543")
                .setPassword("admin123")
                .setRole(UserRole.ADMIN)
                .build();
    }
}