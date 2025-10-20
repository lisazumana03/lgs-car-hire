package za.co.carhire.service.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentMethod;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.repository.reservation.IInvoiceRepository;
import za.co.carhire.service.reservation.impl.InvoiceService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

  @Mock
  private IInvoiceRepository invoiceRepository;

  @InjectMocks
  private InvoiceService invoiceService;

  private Invoice testInvoice;
  private Payment testPayment;
  private Booking testBooking;
  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User.Builder()
            .setUserId(1)
            .setFirstName("John")
            .setLastName("Doe")
            .setEmail("john.doe@example.com")
            .build();

    testBooking = new Booking.Builder()
            .setBookingID(1)
            .setUser(testUser)
            .build();

    testPayment = new Payment.Builder()
            .setPaymentID(1)
            .setBooking(testBooking)
            .setAmount(1500.00)
            .setPaymentMethod(PaymentMethod.CREDIT_CARD)
            .setPaymentStatus(PaymentStatus.PAID)
            .build();

    testInvoice = new Invoice.Builder()
            .setInvoiceID(1)
            .setPayment(testPayment)
            .setBooking(testBooking)
            .setIssueDate(LocalDateTime.now())
            .setDueDate(LocalDateTime.now().plusDays(30))
            .setSubTotal(1304.35)
            .setTaxAmount(195.65)
            .setTotalAmount(1500.00)
            .setStatus("PAID")
            .build();
  }

  @Test
  void createInvoice_ValidInvoice_Success() {
    // Arrange
    when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

    // Act
    Invoice result = invoiceService.create(testInvoice);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getInvoiceID());
    verify(invoiceRepository, times(1)).save(testInvoice);
  }

  @Test
  void readInvoice_ExistingId_ReturnsInvoice() {
    // Arrange
    when(invoiceRepository.findById(1)).thenReturn(Optional.of(testInvoice));

    // Act
    Invoice result = invoiceService.read(1);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getInvoiceID());
    verify(invoiceRepository, times(1)).findById(1);
  }

  @Test
  void readInvoice_NonExistingId_ReturnsNull() {
    // Arrange
    when(invoiceRepository.findById(999)).thenReturn(Optional.empty());

    // Act
    Invoice result = invoiceService.read(999);

    // Assert
    assertNull(result);
    verify(invoiceRepository, times(1)).findById(999);
  }

  @Test
  void updateInvoice_ExistingInvoice_Success() {
    // Arrange
    when(invoiceRepository.existsById(1)).thenReturn(true);
    when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

    // Act
    Invoice result = invoiceService.update(testInvoice);

    // Assert
    assertNotNull(result);
    verify(invoiceRepository, times(1)).save(testInvoice);
  }

  @Test
  void updateInvoice_NonExistingInvoice_ReturnsNull() {
    // Arrange
    when(invoiceRepository.existsById(999)).thenReturn(false);

    // Act
    Invoice result = invoiceService.update(new Invoice.Builder()
            .setInvoiceID(999)
            .setPayment(testPayment)
            .setBooking(testBooking)
            .setIssueDate(LocalDateTime.now())
            .setDueDate(LocalDateTime.now().plusDays(30))
            .setSubTotal(1000.00)
            .setTaxAmount(150.00)
            .setTotalAmount(1150.00)
            .setStatus("PENDING")
            .build());

    // Assert
    assertNull(result);
    verify(invoiceRepository, never()).save(any(Invoice.class));
  }

  @Test
  void deleteInvoice_ValidId_Success() {
    // Arrange
    doNothing().when(invoiceRepository).deleteById(1);

    // Act
    invoiceService.delete(1);

    // Assert
    verify(invoiceRepository, times(1)).deleteById(1);
  }

  @Test
  void getUserInvoices_ValidUserId_ReturnsInvoices() {
    // Arrange
    List<Invoice> expectedInvoices = Arrays.asList(testInvoice);
    when(invoiceRepository.findByBooking_User_UserId(1)).thenReturn(expectedInvoices);

    // Act
    List<Invoice> result = invoiceService.getUserInvoices(1);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1, result.get(0).getInvoiceID());
    verify(invoiceRepository, times(1)).findByBooking_User_UserId(1);
  }

  @Test
  void getUserInvoices_RepositoryException_ReturnsEmptyList() {
    // Arrange
    when(invoiceRepository.findByBooking_User_UserId(999))
            .thenThrow(new RuntimeException("Database error"));

    // Act
    List<Invoice> result = invoiceService.getUserInvoices(999);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void getAllInvoices_ReturnsAllInvoices() {
    // Arrange
    List<Invoice> expectedInvoices = Arrays.asList(testInvoice);
    when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

    // Act
    List<Invoice> result = invoiceService.getAllInvoices();

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(invoiceRepository, times(1)).findAll();
  }

  @Test
  void getInvoicesByPayment_ValidPaymentId_ReturnsInvoices() {
    // Arrange
    List<Invoice> expectedInvoices = Arrays.asList(testInvoice);
    when(invoiceRepository.findByPayment_PaymentID(1)).thenReturn(expectedInvoices);

    // Act
    List<Invoice> result = invoiceService.getInvoicesByPayment(1);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1, result.get(0).getInvoiceID());
    verify(invoiceRepository, times(1)).findByPayment_PaymentID(1);
  }

  @Test
  void getInvoicesByStatus_ValidStatus_ReturnsInvoices() {
    // Arrange
    List<Invoice> expectedInvoices = Arrays.asList(testInvoice);
    when(invoiceRepository.findByStatus("PAID")).thenReturn(expectedInvoices);

    // Act
    List<Invoice> result = invoiceService.getInvoicesByStatus("PAID");

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("PAID", result.get(0).getStatus());
    verify(invoiceRepository, times(1)).findByStatus("PAID");
  }

  @Test
  void getInvoices_ReturnsSetOfInvoices() {
    // Arrange
    List<Invoice> expectedInvoices = Arrays.asList(testInvoice);
    when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

    // Act
    var result = invoiceService.getInvoices();

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(invoiceRepository, times(1)).findAll();
  }

  @Test
  void readInvoice_WithIntegerParameter_Success() {
    // Arrange
    when(invoiceRepository.findById(1)).thenReturn(Optional.of(testInvoice));

    // Act
    Invoice result = invoiceService.read(1);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getInvoiceID());
  }
}