package za.co.carhire.service.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentMethod;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.factory.reservation.PaymentFactory;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.repository.reservation.IPaymentRepository;
import za.co.carhire.service.reservation.IInvoiceService;
import za.co.carhire.service.reservation.impl.PaymentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private IPaymentRepository paymentRepository;

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private IInvoiceService invoiceService;

    @InjectMocks
    private PaymentService paymentService;

    private Payment testPayment;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testBooking = new Booking.Builder()
                .setBookingID(1)
                .build();

        testPayment = new Payment.Builder()
                .setPaymentID(1)
                .setBooking(testBooking)
                .setAmount(1500.00)
                .setPaymentMethod(PaymentMethod.CREDIT_CARD)
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();
    }

    @Test
    void createPayment_ValidPayment_Success() {
        // Arrange
        when(bookingRepository.findById(1)).thenReturn(Optional.of(testBooking));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Payment result = paymentService.create(testPayment);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPaymentID());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void createPayment_BookingNotFound_ThrowsException() {
        // Arrange
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> paymentService.create(testPayment));
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void createPayment_BookingAlreadyHasPayment_ThrowsException() {
        // Arrange
        testBooking.setPayment(testPayment); // Booking already has a payment
        when(bookingRepository.findById(1)).thenReturn(Optional.of(testBooking));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> paymentService.create(testPayment));
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void createPaymentWithParameters_ValidInput_Success() {
        // Arrange
        when(bookingRepository.findById(1)).thenReturn(Optional.of(testBooking));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Payment result = paymentService.createPayment(1, 1500.00, "CREDIT_CARD");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPaymentID());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void createPaymentWithParameters_BookingNotFound_ThrowsException() {
        // Arrange
        when(bookingRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                paymentService.createPayment(999, 1500.00, "CREDIT_CARD"));
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void readPayment_ExistingId_ReturnsPayment() {
        // Arrange
        when(paymentRepository.findById(1)).thenReturn(Optional.of(testPayment));

        // Act
        Payment result = paymentService.read(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPaymentID());
        verify(paymentRepository, times(1)).findById(1);
    }

    @Test
    void readPayment_NonExistingId_ReturnsNull() {
        // Arrange
        when(paymentRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Payment result = paymentService.read(999);

        // Assert
        assertNull(result);
        verify(paymentRepository, times(1)).findById(999);
    }

    @Test
    void updatePayment_ExistingPayment_Success() {
        // Arrange
        when(paymentRepository.existsById(1)).thenReturn(true);
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // Act
        Payment result = paymentService.update(testPayment);

        // Assert
        assertNotNull(result);
        verify(paymentRepository, times(1)).save(testPayment);
    }

    @Test
    void updatePayment_NonExistingPayment_ReturnsNull() {
        // Arrange
        when(paymentRepository.existsById(999)).thenReturn(false);

        // Act
        Payment result = paymentService.update(new Payment.Builder()
                .setPaymentID(999)
                .setBooking(testBooking)
                .setAmount(1000.00)
                .setPaymentMethod(PaymentMethod.CASH)
                .build());

        // Assert
        assertNull(result);
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void updatePaymentStatus_ValidStatus_Success() {
        // Arrange
        Payment pendingPayment = new Payment.Builder()
                .setPaymentID(1)
                .setBooking(testBooking)
                .setAmount(1500.00)
                .setPaymentMethod(PaymentMethod.CREDIT_CARD)
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();

        when(paymentRepository.findById(1)).thenReturn(Optional.of(pendingPayment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(pendingPayment);

        // Act
        Payment result = paymentService.updatePaymentStatus(1, PaymentStatus.PAID);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.PAID, result.getPaymentStatus());
        verify(paymentRepository, times(1)).save(pendingPayment);
    }

    @Test
    void updatePaymentStatus_PaymentNotFound_ThrowsException() {
        // Arrange
        when(paymentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                paymentService.updatePaymentStatus(999, PaymentStatus.PAID));
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void updatePaymentStatus_SameStatus_NoUpdate() {
        // Arrange
        Payment paidPayment = new Payment.Builder()
                .setPaymentID(1)
                .setBooking(testBooking)
                .setAmount(1500.00)
                .setPaymentMethod(PaymentMethod.CREDIT_CARD)
                .setPaymentStatus(PaymentStatus.PAID)
                .build();

        when(paymentRepository.findById(1)).thenReturn(Optional.of(paidPayment));

        // Act
        Payment result = paymentService.updatePaymentStatus(1, PaymentStatus.PAID);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.PAID, result.getPaymentStatus());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void deletePayment_ValidId_Success() {
        // Arrange
        doNothing().when(paymentRepository).deleteById(1);

        // Act
        paymentService.delete(1);

        // Assert
        verify(paymentRepository, times(1)).deleteById(1);
    }

    @Test
    void verifyPayment_ExistingId_ReturnsTrue() {
        // Arrange
        when(paymentRepository.existsById(1)).thenReturn(true);

        // Act
        boolean result = paymentService.verifyPayment(1);

        // Assert
        assertTrue(result);
        verify(paymentRepository, times(1)).existsById(1);
    }

    @Test
    void verifyPayment_NonExistingId_ReturnsFalse() {
        // Arrange
        when(paymentRepository.existsById(999)).thenReturn(false);

        // Act
        boolean result = paymentService.verifyPayment(999);

        // Assert
        assertFalse(result);
        verify(paymentRepository, times(1)).existsById(999);
    }

    @Test
    void verifyAndCreatePayment_ValidInput_Success() {
        // Arrange
        when(bookingRepository.findById(1)).thenReturn(Optional.of(testBooking));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Payment result = paymentService.verifyAndCreatePayment(1, 1500.00, "CREDIT_CARD", "REF123");

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.PAID, result.getPaymentStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void createPayment_WithPaystack_SetsPaidStatus() {
        // Arrange
        Payment paystackPayment = new Payment.Builder()
                .setPaymentID(1)
                .setBooking(testBooking)
                .setAmount(1500.00)
                .setPaymentMethod(PaymentMethod.PAYSTACK)
                .setPaymentStatus(PaymentStatus.PENDING)
                .build();

        when(bookingRepository.findById(1)).thenReturn(Optional.of(testBooking));
        when(paymentRepository.save(any(Payment.class))).thenReturn(paystackPayment);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Payment result = paymentService.create(paystackPayment);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.PAID, result.getPaymentStatus());
    }
}