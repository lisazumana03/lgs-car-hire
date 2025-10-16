package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentMethod;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.util.Helper;

import java.time.LocalDateTime;

/**
 * Factory class for creating Payment objects
 * Updated: Added PayFast payment support (South African payment gateway)
 */
public class PaymentFactory {

    public static Payment createPayment(Booking booking, double amount, PaymentMethod method) {
        if (!isValid(booking, amount, method)) {
            return null;
        }

        // Set PAYFAST payments to PAID status immediately when payment is confirmed
        PaymentStatus status = (method == PaymentMethod.PAYFAST) ?
                PaymentStatus.PAID : PaymentStatus.PENDING;

        return new Payment.Builder()
                .setBooking(booking)
                .setAmount(amount)
                .setPaymentMethod(method)
                .setPaymentStatus(status)
                .setPaymentDate(LocalDateTime.now())
                .setCurrency("ZAR")
                .build();
    }

    public static Payment createPayment(Booking booking, double amount, String method) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(method.toUpperCase());

            // Set PAYFAST payments to PAID status immediately
            PaymentStatus status = ("PAYFAST".equalsIgnoreCase(method)) ?
                    PaymentStatus.PAID : PaymentStatus.PENDING;

            if (!isValid(booking, amount, paymentMethod)) {
                return null;
            }

            return new Payment.Builder()
                    .setBooking(booking)
                    .setAmount(amount)
                    .setPaymentMethod(paymentMethod)
                    .setPaymentStatus(status)
                    .setPaymentDate(LocalDateTime.now())
                    .setCurrency("ZAR")
                    .build();

        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Create a PayFast payment with payment ID
     */
    public static Payment createPayFastPayment(Booking booking, double amount,
                                               String payfastPaymentId, String merchantId) {
        if (!isValid(booking, amount, PaymentMethod.PAYFAST)) {
            return null;
        }

        if (payfastPaymentId == null || payfastPaymentId.trim().isEmpty()) {
            throw new IllegalArgumentException("PayFast Payment ID is required");
        }

        return new Payment.Builder()
                .setBooking(booking)
                .setAmount(amount)
                .setPaymentMethod(PaymentMethod.PAYFAST)
                .setPaymentStatus(PaymentStatus.PAID)
                .setPayfastPaymentId(payfastPaymentId)
                .setMerchantId(merchantId)
                .setPaymentDate(LocalDateTime.now())
                .setCurrency("ZAR")
                .build();
    }

    /**
     * Create a pending PayFast payment (before payment is confirmed)
     */
    public static Payment createPendingPayFastPayment(Booking booking, double amount,
                                                      String payfastPaymentId) {
        if (!isValid(booking, amount, PaymentMethod.PAYFAST)) {
            return null;
        }

        return new Payment.Builder()
                .setBooking(booking)
                .setAmount(amount)
                .setPaymentMethod(PaymentMethod.PAYFAST)
                .setPaymentStatus(PaymentStatus.PENDING)
                .setPayfastPaymentId(payfastPaymentId)
                .setPaymentDate(LocalDateTime.now())
                .setCurrency("ZAR")
                .build();
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
                .setRefundDate(LocalDateTime.now())
                .build();

        refund.setAmount(-payment.getAmount());
        refund.setRefundAmount(payment.getAmount());
        return refund;
    }
}
