package za.co.carhire.util;

import org.apache.commons.validator.routines.EmailValidator;
//import za.co.carhire.domain.reservation.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

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
        if (policyNumber < 1 || policyNumber > 99999999L) {
            return false;
        }
        return true;
    }

    public static boolean isValidNationalIDNumber(String nationalIDNumber) {
        if (nationalIDNumber == null || nationalIDNumber.isEmpty()) {
            return false;
        }

        // Remove any spaces or dashes
        String cleanId = nationalIDNumber.replaceAll("[\\s-]", "");

        // Check if it's exactly 13 digits
        if (!cleanId.matches("\\d{13}")) {
            return false;
        }

        // South African ID number format: YYMMDD 0000 000
        // First 6 digits should be a valid date (YYMMDD)
        try {
            int year = Integer.parseInt(cleanId.substring(0, 2));
            int month = Integer.parseInt(cleanId.substring(2, 4));
            int day = Integer.parseInt(cleanId.substring(4, 6));

            // Basic date validation
            if (month < 1 || month > 12 || day < 1 || day > 31) {
                return false;
            }

            // Convert 2-digit year to 4-digit (assuming 20xx for years 00-29, 19xx for
            // 30-99)
            int fullYear = (year <= 29) ? 2000 + year : 1900 + year;

            // Validate the date using LocalDate
            LocalDate.of(fullYear, month, day);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isRating(int r) {
        if (r < 0 || r > 5) {
            return true;
        }
        return false;
    }

    public static boolean isWithinBoundary(int wb) {
        if (wb < 0 || wb > 1000000) {
            return true;
        }
        return false;
    }

    private static final List<String> VALID_PAYMENT_METHODS = List.of("CREDIT_CARD", "EFT", "CASH", "BANK TRANSFER");

    public static boolean isValidPaymentMethod(String method) {
        return !isNullOrEmpty(method) &&
                VALID_PAYMENT_METHODS.contains(method.toUpperCase());
    }

    // public static boolean isBookingPayable(Booking booking){
    // try {
    // return booking != null &&
    // !isNullOrEmpty(booking.getBookingStatus()) &&
    // !booking.getBookingStatus().equalsIgnoreCase("CANCELLED") &&
    // booking.getEndDate() != null &&
    // booking.getEndDate().isAfter(LocalDateTime.now());
    // } catch (Exception e) {
    // return false;
    // }
    // }
    // public static int generateId(int notificationId) {
    // return (int) (Math.random() * 1000000);
    // }

    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null)
            return 0;
        return java.time.Duration.between(start, end).toDays();
    }

    public static long daysBetween(Date start, Date end) {
        if (start == null || end == null)
            return 0;
        return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static boolean isEmptyOrNull(String str) {
        if (str.isEmpty() || str == null || str.equalsIgnoreCase("null")) {
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

    /**
     * Formats a South African ID number for display
     * 
     * @param idNumber The raw ID number
     * @return Formatted ID number (YYMMDD 0000 000)
     */
    public static String formatSouthAfricanID(String idNumber) {
        if (idNumber == null || idNumber.isEmpty()) {
            return idNumber;
        }

        String cleanId = idNumber.replaceAll("[\\s-]", "");

        if (cleanId.length() != 13) {
            return idNumber; // Return original if not 13 digits
        }

        // Format as YYMMDD 0000 000
        return String.format("%s %s %s",
                cleanId.substring(0, 6),
                cleanId.substring(6, 10),
                cleanId.substring(10, 13));
    }

}
