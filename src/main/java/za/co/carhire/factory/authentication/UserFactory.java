package za.co.carhire.factory.authentication;

import za.co.carhire.util.Helper;
import za.co.carhire.domain.authentication.User;
/* User.java

     User POJO class

     Author: Bonga Velem (220052379)

     Date: 18 May 2025 */

import java.time.LocalDate;

public class UserFactory {

    public static User createUser(String idNumber, String name, String email, String dateOfBirth, String phoneNumber, String password, String licenseNumber) {



        if (Helper.isEmptyOrNull(name) ||
                !Helper.isValidNationalIDNumber(idNumber) ||
                !Helper.isValidEmail(email) ||
                !Helper.isValidDate(dateOfBirth) ||
                Helper.isEmptyOrNull(phoneNumber) ||
                Helper.isEmptyOrNull(password) ||
                Helper.isEmptyOrNull(licenseNumber)) {
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

    public User buildUser(int i, long l, String johnDoe, String mail, LocalDate of, String number, String pass123, String l123456) {
        return null;
    }
}



