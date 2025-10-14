package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.*;
import za.co.carhire.util.Helper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class InvoiceFactory {
    public static Invoice generateInvoice(Payment payment, Booking booking) {
        if (!isValid(payment, booking)) {
            return null;
        }

        // Calculate rental period in days
        long rentalDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        if (rentalDays < 1) rentalDays = 1;

        // Calculate amounts with tax INCLUDED in the total
        double totalAmount = payment.getAmount(); // Total is the payment amount
        double subTotal = totalAmount / 1.15; // Back-calculate subtotal
        double taxAmount = totalAmount - subTotal; // Tax is the difference

        // Set invoice status based on payment status
        String invoiceStatus = payment.getPaymentStatus() == PaymentStatus.PAID ? "PAID" : "PENDING";

        return new Invoice.Builder()
                .setPayment(payment)
                .setBooking(booking)
                .setIssueDate(LocalDateTime.now())
                .setDueDate(booking.getEndDate())
                .setSubTotal(roundToTwoDecimals(subTotal))
                .setTaxAmount(roundToTwoDecimals(taxAmount))
                .setTotalAmount(totalAmount)
                .setStatus(invoiceStatus) // Set based on payment status
                .build();
    }

    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private static boolean isValid(Payment payment, Booking booking) {
        return payment != null &&
                booking != null &&
                payment.getBooking() != null &&
                payment.getBooking().getBookingID() == booking.getBookingID() &&
                payment.getAmount() > 0 &&
                booking.getStartDate() != null &&
                booking.getEndDate() != null &&
                booking.getEndDate().isAfter(booking.getStartDate());
    }
}