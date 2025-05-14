package za.co.carhire.util;

import java.time.LocalDateTime;

public class Helper {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
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
    public static boolean isRating(int r){
        if(r<0 || r>5){
            return true;
        }return false;
    }
    public static boolean isWithinBoundary(int wb){
        if(wb<0 || wb>1000000){
            return true;
        }return false;
    }
}
