package za.co.carhire.factory.reservation;
/* InvoiceFactory.java
 * InvoiceFactory class
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */

import za.co.carhire.domain.reservation.*;
import za.co.carhire.util.Helper;
import java.time.LocalDateTime;

public class InvoiceFactory {
    public static Invoice generateInvoice(Payment payment, Booking booking) {
        if (!isValid(payment, booking)) {
            return null;
        }

        double subTotal = calculateSubTotal(booking);
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

    private static double calculateSubTotal(Booking booking) {
        if (booking == null || booking.getCar() == null || booking.getCar().isEmpty() ||
                booking.getStartDate() == null || booking.getEndDate() == null) {
            return 0.0;
        }

        long days = Helper.daysBetween(booking.getStartDate(), booking.getEndDate());
        days = Math.max(1, days); // Minimum 1 day charge

        long finalDays = days;
        return booking.getCar().stream()
                .filter(car -> car != null && car.getRentalPrice() > 0)
                .mapToDouble(car -> car.getRentalPrice() * finalDays)
                .sum();
    }
    private static boolean isValid(Payment payment, Booking booking) {
        return payment != null &&
                booking != null &&
                payment.getBooking() != null &&
                payment.getBooking().getBookingID() == booking.getBookingID() &&
                !"REFUNDED".equals(payment.getPaymentMethod()) &&
                booking.getEndDate() != null &&
                booking.getEndDate().isAfter(LocalDateTime.now());  // Changed after() to isAfter()
    }
}
