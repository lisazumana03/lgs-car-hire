package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class InvoiceFactory {
    public static Invoice generateInvoice(Payment payment, Booking booking) {
        if (!isValid(payment, booking)) {
            System.err.println("ERROR: Invoice validation failed");
            System.err.println("  Payment: " + (payment != null ? "exists" : "null"));
            if (payment != null) {
                System.err.println("  Payment amount: " + payment.getAmount());
            }
            System.err.println("  Booking: " + (booking != null ? "exists" : "null"));
            if (booking != null) {
                System.err.println("  Booking start: " + booking.getStartDate());
                System.err.println("  Booking end: " + booking.getEndDate());
            }
            return null;
        }

        // Use the payment amount as the total amount (it already includes everything)
        double totalAmount = payment.getAmount();

        // Calculate subtotal by removing tax (assuming 15% tax is already included in
        // payment amount)
        // Formula: totalAmount = subTotal + (subTotal * 0.15) = subTotal * 1.15
        // Therefore: subTotal = totalAmount / 1.15
        double subTotal = totalAmount / 1.15;
        double taxAmount = totalAmount - subTotal;

        // Set invoice status based on payment status
        String invoiceStatus = payment.getPaymentStatus() == PaymentStatus.PAID ? "PAID" : "PENDING";

        System.out.println("Invoice calculation:");
        System.out.println("  Total Amount (from payment): " + totalAmount);
        System.out.println("  Sub Total: " + subTotal);
        System.out.println("  Tax Amount: " + taxAmount);

        return new Invoice.Builder()
                .setPayment(payment)
                .setBooking(booking)
                .setIssueDate(LocalDateTime.now())
                .setDueDate(booking.getEndDate())
                .setSubTotal(roundToTwoDecimals(subTotal))
                .setTaxAmount(roundToTwoDecimals(taxAmount))
                .setTotalAmount(roundToTwoDecimals(totalAmount))
                .setStatus(invoiceStatus) // Set based on payment status
                .build();
    }

    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private static boolean isValid(Payment payment, Booking booking) {
        return payment != null &&
                booking != null &&
                payment.getAmount() > 0 &&
                booking.getStartDate() != null &&
                booking.getEndDate() != null &&
                (booking.getEndDate().isAfter(booking.getStartDate()) ||
                        booking.getEndDate().isEqual(booking.getStartDate()));
    }
}