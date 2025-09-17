package za.co.carhire.factory.reservation;

/* PaymentFactory.java
 * PaymentFactory class
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentMethod;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.util.Helper;

public class PaymentFactory {
    public static Payment createPayment(Booking booking, double amount, PaymentMethod method) {
        if (!isValid(booking, amount, method)) {
            return null;
        }

        return new Payment.Builder()
                .setBooking(booking)
                .setAmount(amount)
                .setPaymentMethod(method)
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();
    }

    public static Payment createPayment(Booking booking, double amount, String method) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(method.toUpperCase());
            return createPayment(booking, amount, paymentMethod);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // Fixed isValid method for PaymentMethod
    private static boolean isValid(Booking booking, double amount, PaymentMethod method) {
        return booking != null &&
                booking.getPayment() == null &&
                amount > 0 &&
                method != null;
    }


    public static Payment processRefund(Payment payment) {
        if (payment == null) {
            return null;
        }

        Payment refund = new Payment.Builder()
                .copy(payment)
                .setPaymentStatus(PaymentStatus.REFUNDED)
                .build();

        refund.setAmount(-payment.getAmount()); // Negative amount for refund
        return refund;

    }
}
