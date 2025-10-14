package za.co.carhire.factory.reservation;

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

        // FIX: Set PAYSTACK payments to PAID status immediately
        PaymentStatus status = (method == PaymentMethod.PAYSTACK) ?
                PaymentStatus.PAID : PaymentStatus.PENDING;

        return new Payment.Builder()
                .setBooking(booking)
                .setAmount(amount)
                .setPaymentMethod(method)
                .setPaymentStatus(status) // Use the correct status
                .build();
    }

    public static Payment createPayment(Booking booking, double amount, String method) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(method.toUpperCase());

            // FIX: Set PAYSTACK payments to PAID status immediately
            PaymentStatus status = ("PAYSTACK".equalsIgnoreCase(method)) ?
                    PaymentStatus.PAID : PaymentStatus.PENDING;

            if (!isValid(booking, amount, paymentMethod)) {
                return null;
            }

            return new Payment.Builder()
                    .setBooking(booking)
                    .setAmount(amount)
                    .setPaymentMethod(paymentMethod)
                    .setPaymentStatus(status) // Use the correct status
                    .build();

        } catch (IllegalArgumentException e) {
            return null;
        }
    }

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

        refund.setAmount(-payment.getAmount());
        return refund;
    }
}