package za.co.carhire.factory.reservation;

/* InvoiceFactoryTest.java
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.domain.vehicle.Car;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceFactoryTest {

        private final LocalDateTime now = LocalDateTime.now();
        private final LocalDateTime tomorrow = now.plusDays(1);

        private User createTestUser() {
                return new User.Builder()
                        .setUserId(1)
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setEmail("john.doe@example.com")
                        .setIdNumber(123456789L)
                        .setDateOfBirth(LocalDate.of(1990, 1, 1))
                        .setPhoneNumber("1234567890")
                        .setPassword("password")
                        .setRole(Role.CUSTOMER)
                        .build();
        }

        private Car createTestCar() {
                return new Car.Builder()
                        .setCarID(1)
                        .setBrand("Toyota")
                        .setModel("Corolla")
                        .setYear(2023)
                        .setRentalPrice(500.00)
                        .setAvailability(true)
                        .build();
        }

        private Booking createValidBooking() {
                return new Booking.Builder()
                        .setBookingID(1)
                        .setUser(createTestUser())
                        .setCar(createTestCar())
                        .setBookingDateAndTime(now)
                        .setStartDate(now)
                        .setEndDate(tomorrow)
                        .setBookingStatus(BookingStatus.CONFIRMED)
                        .build();
        }

        private Payment createPaidPayment(double amount) {
                return new Payment.Builder()
                        .setPaymentID(1)
                        .setBooking(createValidBooking())
                        .setPaymentMethod(PaymentMethod.CREDIT_CARD)
                        .setPaymentStatus(PaymentStatus.PAID)
                        .setAmount(amount)
                        .build();
        }

        @Test
        void generateValidInvoice() {
                Payment payment = createPaidPayment(575.00); // 500 + 15% tax
                Booking booking = createValidBooking();

                Invoice invoice = InvoiceFactory.generateInvoice(payment, booking);

                assertNotNull(invoice);
                assertEquals(575.00, invoice.getTotalAmount());
                assertEquals(500.00, invoice.getSubTotal(), 0.01);
                assertEquals(75.00, invoice.getTaxAmount(), 0.01);
                assertEquals("PAID", invoice.getStatus());
                assertEquals(booking.getEndDate(), invoice.getDueDate());
        }

        @Test
        void generateInvoice_WithPendingPayment_SetsPendingStatus() {
                Payment pendingPayment = new Payment.Builder()
                        .setPaymentID(1)
                        .setBooking(createValidBooking())
                        .setPaymentMethod(PaymentMethod.CREDIT_CARD)
                        .setPaymentStatus(PaymentStatus.PENDING)
                        .setAmount(575.00)
                        .build();

                Booking booking = createValidBooking();
                Invoice invoice = InvoiceFactory.generateInvoice(pendingPayment, booking);

                assertNotNull(invoice);
                assertEquals("PENDING", invoice.getStatus());
        }

        @Test
        void generateInvoice_WithNullPayment_ReturnsNull() {
                Booking booking = createValidBooking();
                Invoice invoice = InvoiceFactory.generateInvoice(null, booking);

                assertNull(invoice);
        }

        @Test
        void generateInvoice_WithNullBooking_ReturnsNull() {
                Payment payment = createPaidPayment(575.00);
                Invoice invoice = InvoiceFactory.generateInvoice(payment, null);

                assertNull(invoice);
        }

        @Test
        void generateInvoice_WithInvalidAmount_ReturnsNull() {
                Payment invalidPayment = new Payment.Builder()
                        .setPaymentID(1)
                        .setBooking(createValidBooking())
                        .setPaymentMethod(PaymentMethod.CREDIT_CARD)
                        .setPaymentStatus(PaymentStatus.PAID)
                        .setAmount(0.00) // Invalid amount
                        .build();

                Booking booking = createValidBooking();
                Invoice invoice = InvoiceFactory.generateInvoice(invalidPayment, booking);

                assertNull(invoice);
        }

        @Test
        void generateInvoice_WithInvalidDates_ReturnsNull() {
                Payment payment = createPaidPayment(575.00);

                Booking invalidBooking = new Booking.Builder()
                        .setBookingID(1)
                        .setUser(createTestUser())
                        .setCar(createTestCar())
                        .setBookingDateAndTime(now)
                        .setStartDate(tomorrow) // Start after end
                        .setEndDate(now)
                        .setBookingStatus(BookingStatus.CONFIRMED)
                        .build();

                Invoice invoice = InvoiceFactory.generateInvoice(payment, invalidBooking);

                assertNull(invoice);
        }

        @Test
        void generateInvoice_WithSameDayBooking() {
                Booking sameDayBooking = new Booking.Builder()
                        .setBookingID(1)
                        .setUser(createTestUser())
                        .setCar(createTestCar())
                        .setBookingDateAndTime(now)
                        .setStartDate(now)
                        .setEndDate(now) // Same day
                        .setBookingStatus(BookingStatus.CONFIRMED)
                        .build();

                Payment payment = createPaidPayment(575.00);

                Invoice invoice = InvoiceFactory.generateInvoice(payment, sameDayBooking);

                assertNotNull(invoice);
                assertEquals(575.00, invoice.getTotalAmount());
        }
}