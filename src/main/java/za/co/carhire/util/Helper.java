package za.co.carhire.util;

import org.apache.commons.validator.routines.EmailValidator;
import za.co.carhire.domain.reservation.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class Helper {
    public static boolean isNullOrEmpty(BookingStatus s) {
        return s == null;
    }
    public static boolean isValidPostalCode(short postalCode) {
        if (postalCode < -1 || postalCode > 9999) {
            return false;
        }
        return true;
    }
    public static boolean isValidPolicyNumber(long policyNumber) {
        if (policyNumber < 1 || policyNumber > 99999999L) {
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
    public static boolean isWithinBoundary(int wb) {
        if (wb < 0 || wb > 1000000) {
            return true;
        }
        return false;
    }
    private static final List<String> VALID_PAYMENT_METHODS =
            List.of("CREDIT_CARD", "EFT", "CASH", "BANK TRANSFER");

    public static boolean isValidPaymentMethod(String method) {
        return !isNullOrEmpty(method) &&
                VALID_PAYMENT_METHODS.contains(method.toUpperCase());
    }


    public static int generateId() {
        return (int) (Math.random() * 1000000);
    }

    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return java.time.Duration.between(start, end).toDays();
    }

    public static long daysBetween(Date start, Date end) {
        if (start == null || end == null) return 0;
        return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static boolean isEmptyOrNull(BookingStatus status) {
        if(status == null || status.equals(BookingStatus.PENDING) || status.equals(BookingStatus.CANCELLED)){
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrNull(String str) {
        if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrNull(int value) {
        if (value > 0 || isNullOrEmpty("null")) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(String str) {
        return isEmptyOrNull(str);
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
        if (email == null || !email.contains("@")) {
            return false;
        }

        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    //Check if object is null
    public static boolean isNull(Object obj) {
        return obj == null;
    }


}
