package za.co.carhire.factory.authentication;

/* UserFactory.java

     UserFactory/factory class

     Author: Bonga Velem

     Student Number: 220052379

     */
import za.co.carhire.util.Helper;
import za.co.carhire.domain.authentication.User;
import java.time.LocalDate;

public class UserFactory {

    public static User createUser(String idNumber, String firstName, String lastName, String email, String dateOfBirth,
            String phoneNumber, String password) {
        if (Helper.isEmptyOrNull(firstName) ||
                Helper.isEmptyOrNull(lastName) ||
                !Helper.isValidNationalIDNumber(idNumber) ||
                !Helper.isValidEmail(email) ||
                !Helper.isValidDate(dateOfBirth) ||
                Helper.isEmptyOrNull(phoneNumber) ||
                Helper.isEmptyOrNull(password)) {
            return null;
        }

        return new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setIdNumber(Long.valueOf(idNumber))
                .setEmail(email)
                .setDateOfBirth(LocalDate.parse(dateOfBirth))
                .setPhoneNumber(phoneNumber)
                .setPassword(password)
                .build();
    }

    public static User loginUser(String email, String password) {
        if (!Helper.isValidEmail(email) || Helper.isEmptyOrNull(password)) {
            return null;
        }

        return new User.Builder()
                .setEmail(email)
                .setPassword(password).build();
    }

    public static Integer createUserId() {
        return Helper.generateId();
    }

    public User buildUser(int i, long l, String johnDoe, String mail, LocalDate of, String number, String pass123,
            String l123456) {
        return null;
    }
}
