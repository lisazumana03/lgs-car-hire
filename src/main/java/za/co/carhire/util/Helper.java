package za.co.carhire.util;

import java.time.LocalDateTime;

public class Helper {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty() || s.length() == 3;
    }
    //Email Validator
    public static boolean isValidPostalCode(short postalCode) {
        if (postalCode < -1 || postalCode > 9999) {
            return false;
        }
        return true;
    }
    //Date Validator
}
