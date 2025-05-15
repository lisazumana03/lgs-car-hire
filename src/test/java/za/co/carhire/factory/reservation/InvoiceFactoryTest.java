package za.co.carhire.factory.reservation;
/* InvoiceFactoryTest.java
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.domain.vehicle.Car;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceFactoryTest {

    private Car testCar = new Car.Builder()
            .setRentalPrice(500.00)
            .build();

    private Booking validBooking = new Booking.Builder()
            .setBookingID(1)
            .setCar(List.of(testCar))
            .setStartDate(new Date())
            .setEndDate(new Date(System.currentTimeMillis() + 86400000)) // +1 day
            .build();

    private Payment paidPayment = new Payment.Builder()
            .setBookingID(validBooking)
            .setPaymentMethod("EFT")
            .setAmount(575.00) // 500 + 15% tax
            .build();

    @Test
    void generateValidInvoice() {
        Invoice invoice = InvoiceFactory.generateInvoice(paidPayment, validBooking);
        assertNotNull(invoice);
        assertEquals(575.00, invoice.getTotalAmount());
        assertEquals(validBooking.getEndDate(), invoice.getDueDate());
    }

    @Test
    void rejectUnpaidPayment() {
        Payment unpaid = new Payment.Builder()
                .setBookingID(validBooking)
                .setPaymentMethod("PENDING")
                .build();
        assertNull(InvoiceFactory.generateInvoice(unpaid, validBooking));
    }

    @Test
    void calculateCorrectSubtotal() {
        // 2 cars for 3 days: (500 + 300) * 3 = 2400
        Booking booking = new Booking.Builder()
                .setCar(List.of(
                        new Car.Builder().setRentalPrice(500.00).build(),
                        new Car.Builder().setRentalPrice(300.00).build()
                ))
                .setStartDate(new Date())
                .setEndDate(new Date(System.currentTimeMillis() + 259200000)) // +3 days
                .build();

        Invoice invoice = InvoiceFactory.generateInvoice(paidPayment, booking);
        assertEquals(2400.00, invoice.getSubTotal());
        assertEquals(2760.00, invoice.getTotalAmount()); // 2400 + 15%
    }

    @Test
    void handleMinimumOneDayCharge() {
        // Same-day return
        Booking booking = new Booking.Builder()
                .setCar(List.of(testCar))
                .setStartDate(new Date())
                .setEndDate(new Date()) // Same day
                .build();

        Invoice invoice = InvoiceFactory.generateInvoice(paidPayment, booking);
        assertEquals(500.00, invoice.getSubTotal()); // Still charged for 1 day
    }
}