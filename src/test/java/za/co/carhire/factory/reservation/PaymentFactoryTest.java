package za.co.carhire.factory.reservation;

/* PaymentFactoryTest.java
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentMethod;
import za.co.carhire.domain.reservation.PaymentStatus;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {

    private Booking createValidBooking() {
        return new Booking.Builder()
                .setBookingID(1)
                .setBookingStatus(BookingStatus.CONFIRMED)
                .build();
    }

    @Test
    void createValidPayment_WithEnum() {
        Booking booking = createValidBooking();
        Payment payment = PaymentFactory.createPayment(booking, 1500.00, PaymentMethod.CREDIT_CARD);

        assertNotNull(payment);
        assertEquals(1500.00, payment.getAmount());
        assertEquals(PaymentMethod.CREDIT_CARD, payment.getPaymentMethod());
        assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus());
    }

    @Test
    void createValidPayment_WithString() {
        Booking booking = createValidBooking();
        Payment payment = PaymentFactory.createPayment(booking, 2000.00, "DEBIT_CARD");

        assertNotNull(payment);
        assertEquals(2000.00, payment.getAmount());
        assertEquals(PaymentMethod.DEBIT_CARD, payment.getPaymentMethod());
    }

    @Test
    void createPayment_WithPaystack_SetsPaidStatus() {
        Booking booking = createValidBooking();
        Payment payment = PaymentFactory.createPayment(booking, 2500.00, "PAYSTACK");

        assertNotNull(payment);
        assertEquals(PaymentMethod.PAYSTACK, payment.getPaymentMethod());
        assertEquals(PaymentStatus.PAID, payment.getPaymentStatus());
    }

    @Test
    void createPayment_WithInvalidAmount_ReturnsNull() {
        Booking booking = createValidBooking();
        Payment payment = PaymentFactory.createPayment(booking, -100.00, "CREDIT_CARD");

        assertNull(payment);
    }

    @Test
    void createPayment_WithZeroAmount_ReturnsNull() {
        Booking booking = createValidBooking();
        Payment payment = PaymentFactory.createPayment(booking, 0.00, "CREDIT_CARD");

        assertNull(payment);
    }

    @Test
    void createPayment_WithInvalidMethod_ReturnsNull() {
        Booking booking = createValidBooking();
        Payment payment = PaymentFactory.createPayment(booking, 1000.00, "INVALID_METHOD");

        assertNull(payment);
    }

    @Test
    void createPayment_WithCancelledBooking_ReturnsNull() {
        Booking cancelledBooking = new Booking.Builder()
                .setBookingStatus(BookingStatus.CANCELLED)
                .build();

        Payment payment = PaymentFactory.createPayment(cancelledBooking, 1000.00, "CASH");

        assertNull(payment);
    }

    @Test
    void createPayment_WithNullBooking_ReturnsNull() {
        Payment payment = PaymentFactory.createPayment(null, 1000.00, "CASH");

        assertNull(payment);
    }

    @Test
    void processRefund_ValidPayment() {
        Booking booking = createValidBooking();
        Payment originalPayment = PaymentFactory.createPayment(booking, 2000.00, "EFT");

        Payment refund = PaymentFactory.processRefund(originalPayment);

        assertNotNull(refund);
        assertEquals(PaymentMethod.REFUND, refund.getPaymentMethod());
        assertEquals(PaymentStatus.REFUNDED, refund.getPaymentStatus());
        assertEquals(-2000.00, refund.getAmount());
    }

    @Test
    void processRefund_NullPayment_ReturnsNull() {
        Payment refund = PaymentFactory.processRefund(null);

        assertNull(refund);
    }

    @Test
    void createPayment_WithSpacesInMethod_NormalizesCorrectly() {
        Booking booking = createValidBooking();
        Payment payment = PaymentFactory.createPayment(booking, 1500.00, "credit card");

        assertNotNull(payment);
        assertEquals(PaymentMethod.CREDIT_CARD, payment.getPaymentMethod());
    }
}