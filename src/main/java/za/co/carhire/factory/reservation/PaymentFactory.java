package za.co.carhire.factory.reservation;
/* PaymentFactory.java
 * PaymentFactory class
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.util.Helper;


public class PaymentFactory {
    public static Payment createPayment(Booking booking, double amount, String method) {
        if (!isValid(booking, amount, method)) {
            return null;
        }

        return new Payment.Builder()
                .setPaymentID(Helper.generateId()).setAmount(amount)
                .setPaymentMethod(method.toUpperCase())
                .build();
    }

    private static boolean isValid(Booking booking, double amount, String method) {
        return true;
    }

    public static Payment processRefund(Payment payment) {
        if (payment == null || payment.getPaymentMethod().equalsIgnoreCase("REFUND")) {
            return null;
        }

        return new Payment.Builder()
                .copy(payment)
                .setPaymentMethod("REFUND")
                .build();
    }

//    public static boolean isValid(Booking booking, double amount, String method) {
//        return booking != null &&
//                !Helper.isNullOrEmpty(booking.getBookingStatus()) &&
//                !booking.getBookingStatus().equalsIgnoreCase("CANCELLED") &&
//                amount > 0 &&
//                Helper.isValidPaymentMethod(method);
//    }
}

