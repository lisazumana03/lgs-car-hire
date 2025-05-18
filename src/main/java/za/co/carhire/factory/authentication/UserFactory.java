package za.co.carhire.factory.authentication;

import za.co.carhire.Util.validationHelper;
import za.co.carhire.domain.authentication.User;
/* User.java

     User POJO class

     Author: Bonga Velem (220052379)

     Date: 18 May 2025 */

import java.time.LocalDate;

import static javax.swing.text.html.parser.DTDConstants.ID;

public class UserFactory {

    public static User createUser(Long idNumber, String name, String email, String dateOfBirth, String phoneNumber, String password, String licenseNumber) {
        String userId = validationHelper.generateId();


        if (validationHelper.isEmptyOrNull(name) ||
                !validationHelper.isValidEmail(email) ||
                validationHelper.isValidDate(dateOfBirth)||
                validationHelper.isEmptyOrNull(phoneNumber) ||
                validationHelper.isEmptyOrNull(password) ||
                validationHelper.isEmptyOrNull(licenseNumber)) {
            return null;
        }


        return new User.Builder()
                .setName(name)
                .setIdNumber(idNumber)
                .setEmail(email)
                .setDateOfBirth(LocalDate.parse(dateOfBirth))
                .setPhoneNumber(phoneNumber)
                .setPassword(password)
                .setLicenseNumber(licenseNumber)
                .build();
    }


    public static User loginUser(String email, String password) {
        if (!validationHelper.isValidEmail(email) || validationHelper.isEmptyOrNull(password)) {
                return null;
            }

            return new User.Builder()
                    .setEmail(email)
                    .setPassword(password).build();
        }

    }



