package za.co.carhire.Util;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/* User.java

     User POJO class

     Author: Bonga Velem (220052379)

     Date: 18 May 2025 */
//Bonga Velem's helper class
import static java.util.UUID.randomUUID;

public class validationHelper {

    public static boolean isEmptyOrNull(String str) {
        if (str.isEmpty() ||str == null || str.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }
    public static boolean isValidDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }

        try {
            LocalDate.parse(dateString); // Uses format: yyyy-MM-dd
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    // The following method uses a singleton pattern to validate an email address
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            return false;
        }

        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }


    public static String generateId(){
        return  randomUUID().toString();

    }



    //	•	If it’s null, return the current timestamp (LocalDateTime.now()).
public static boolean isDateSent(LocalDate dateSent) {
    return dateSent == null;
}

}
