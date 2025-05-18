package za.co.carhire.Util;

import za.co.carhire.domain.reservation.Booking;

import java.time.LocalDateTime;
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
    private static final List<String> VALID_PAYMENT_METHODS =
            List.of("CREDIT_CARD", "EFT", "CASH", "BANK TRANSFER");

    public static boolean isValidPaymentMethod(String method) {
        return !isNullOrEmpty(method) &&
                VALID_PAYMENT_METHODS.contains(method.toUpperCase());
    }

    public static boolean isBookingPayable(Booking booking) {
        try {
            return booking != null &&
                    !isNullOrEmpty(booking.getBookingStatus()) &&
                    !booking.getBookingStatus().equalsIgnoreCase("CANCELLED") &&
                    booking.getEndDate() != null &&
                    booking.getEndDate().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public static int generateId() {
        return (int) (Math.random() * 1000000);
    }

    public static long daysBetween(Date start, Date end) {
        return (end.getTime() - start.getTime());
    }
}
