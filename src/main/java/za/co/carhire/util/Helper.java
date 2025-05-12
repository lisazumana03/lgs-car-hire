package za.co.carhire.util;

import java.time.LocalDateTime;

public class Helper {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty() || s.length() == 3;
    }
    public static boolean isValidPostalCode(short postalCode) {
        if (postalCode < -1 || postalCode > 9999) {
            return false;
        }
        return true;
    }
    public static boolean isValidPolicyNumber(long policyNumber) {
        if (policyNumber < 1 || policyNumber > 999999999) {
            return false;
        }
        return true;
    }
    public static boolean isValidNationalIDNumber(String nationalIDNumber) {
        if (nationalIDNumber == null || nationalIDNumber.isEmpty()) {
            return false;
        }
        return true;
    }
}
