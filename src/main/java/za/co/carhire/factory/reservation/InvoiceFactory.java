package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.*;
import za.co.carhire.util.Helper;
import java.time.LocalDateTime;

public class InvoiceFactory {
    public static Invoice generateInvoice(Payment payment, Booking booking) {
        if (!isValid(payment, booking)) {
            return null;
        }

        // Use payment amount instead of calculating from booking
        double subTotal = payment.getAmount();
        double taxAmount = subTotal * 0.15;
        double totalAmount = subTotal + taxAmount;

        return new Invoice.Builder()
                .setPayment(payment)
                .setBooking(booking)
                .setIssueDate(LocalDateTime.now())
                .setDueDate(booking.getEndDate())
                .setSubTotal(subTotal)
                .setTaxAmount(taxAmount)
                .setTotalAmount(totalAmount)
                .setStatus("PENDING")
                .build();
    }

    private static boolean isValid(Payment payment, Booking booking) {
        return payment != null &&
                booking != null &&
                payment.getBooking() != null &&
                payment.getBooking().getBookingID() == booking.getBookingID() &&
                payment.getAmount() > 0 &&
                booking.getEndDate() != null &&
                booking.getEndDate().isAfter(LocalDateTime.now());
    }
}