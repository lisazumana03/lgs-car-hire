package za.co.carhire.factory.reservation;
/* InvoiceFactoryTest.java
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.domain.vehicle.Car;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceFactoryTest {
        private final LocalDateTime now = LocalDateTime.now();
        private final LocalDateTime tomorrow = now.plusDays(1);
        private final LocalDateTime threeDaysLater = now.plusDays(3);

        private Car testCar = new Car.Builder()
                        .setRentalPrice(500.00)
                        .build();

        private Booking validBooking = new Booking.Builder()
                        .setBookingID(1)
                        .setCar(List.of(testCar))
                        .setStartDate(now)
                        .setEndDate(tomorrow)
                        .build();

        private Payment paidPayment = new Payment.Builder()
                        .setPaymentID(1)
                        .setBooking(validBooking)
                        .setPaymentMethod(PaymentMethod.EFT)
                        .setAmount(575.00) // 500 + 15% tax
                        .build();

        @Test
        void generateValidInvoice() {
                Invoice invoice = InvoiceFactory.generateInvoice(paidPayment, validBooking);
                assertNotNull(invoice);
                // Total amount should match payment amount
                assertEquals(575.00, invoice.getTotalAmount());
                assertEquals(validBooking.getEndDate(), invoice.getDueDate());
                // Verify subtotal and tax are calculated correctly from payment amount
                assertEquals(500.00, invoice.getSubTotal(), 0.01); // 575 / 1.15 = 500
                assertEquals(75.00, invoice.getTaxAmount(), 0.01); // 575 - 500 = 75
        }

        @Test
        void rejectUnpaidPayment() {
                Payment unpaid = new Payment.Builder()
                                .setPaymentID(2)
                                .setBooking(validBooking)
                                .setPaymentMethod(PaymentMethod.CASH)
                                .build();
                assertNull(InvoiceFactory.generateInvoice(unpaid, validBooking));
        }

        @Test
        void calculateCorrectSubtotal() {
                // Create payment with total amount 2760.00 (includes tax)
                Booking booking = new Booking.Builder()
                                .setCar(List.of(
                                                new Car.Builder().setRentalPrice(500.00).build(),
                                                new Car.Builder().setRentalPrice(300.00).build()))
                                .setStartDate(now)
                                .setEndDate(threeDaysLater)
                                .build();

                Payment payment = new Payment.Builder()
                                .setPaymentID(3)
                                .setBooking(booking)
                                .setPaymentMethod(PaymentMethod.EFT)
                                .setAmount(2760.00) // Total with tax
                                .build();

                Invoice invoice = InvoiceFactory.generateInvoice(payment, booking);
                // Subtotal is calculated as: totalAmount / 1.15
                assertEquals(2400.00, invoice.getSubTotal(), 0.01); // 2760 / 1.15 = 2400
                assertEquals(360.00, invoice.getTaxAmount(), 0.01); // 2760 - 2400 = 360
                assertEquals(2760.00, invoice.getTotalAmount()); // Matches payment amount
        }

        @Test
        void handleMinimumOneDayCharge() {
                // Same-day return - invoice now uses payment amount
                Booking booking = new Booking.Builder()
                                .setCar(List.of(testCar))
                                .setStartDate(now)
                                .setEndDate(now) // Same day
                                .build();

                // Payment amount is what determines invoice amounts now
                Payment payment = new Payment.Builder()
                                .setPaymentID(4)
                                .setBooking(booking)
                                .setPaymentMethod(PaymentMethod.EFT)
                                .setAmount(575.00) // Payment amount with tax
                                .build();

                Invoice invoice = InvoiceFactory.generateInvoice(payment, booking);
                assertEquals(500.00, invoice.getSubTotal(), 0.01); // 575 / 1.15 = 500
                assertEquals(575.00, invoice.getTotalAmount()); // Matches payment amount
        }
}