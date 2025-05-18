package za.co.carhire.factory.reservation;
/* PaymentFactoryTest.java
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Payment;
import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {
    private Booking validBooking = new Booking.Builder()
            .setBookingID(1)
            .setBookingStatus("CONFIRMED")
            .build();

    @Test
    void createValidPayment() {
        Payment payment = PaymentFactory.createPayment(validBooking, 1500.00, "CREDIT CARD");
        assertNotNull(payment);
        assertEquals(1500.00, payment.getAmount());
        assertEquals("CREDIT CARD", payment.getPaymentMethod());
    }

    @Test
    void rejectInvalidAmount() {
        assertNull(PaymentFactory.createPayment(validBooking, -100, "EFT"));
    }

    @Test
    void rejectInvalidPaymentMethod() {
        assertNull(PaymentFactory.createPayment(validBooking, 1000.00, "INVALID"));
    }

    @Test
    void rejectCancelledBooking() {
        Booking cancelledBooking = new Booking.Builder()
                .setBookingStatus("CANCELLED")
                .build();
        assertNull(PaymentFactory.createPayment(cancelledBooking, 1000.00, "CASH"));
    }

    @Test
    void processValidRefund() {
        Payment payment = PaymentFactory.createPayment(validBooking, 2000.00, "EFT");
        Payment refund = PaymentFactory.processRefund(payment);
        assertEquals("REFUND", refund.getPaymentMethod());
    }
}
