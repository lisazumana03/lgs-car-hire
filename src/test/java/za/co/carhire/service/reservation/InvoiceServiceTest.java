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
import za.co.carhire.repository.reservation.IInvoiceRepository;
import za.co.carhire.service.reservation.impl.InvoiceService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * InvoiceServiceTest
 * 
 * Unit tests for InvoiceService
 * 
 * Author: Test Suite
 * Comprehensive service layer testing for invoices
 */
@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

  @Mock
  private IInvoiceRepository invoiceRepository;

  @InjectMocks
  private InvoiceService invoiceService;

  private Invoice testInvoice;
  private Payment testPayment;
  private Booking testBooking;

  @BeforeEach
  void setUp() {
    testBooking = new Booking();

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
        .setSubTotal(1500.00)
        .setTaxAmount(225.00)
        .setTotalAmount(1725.00)
        .setStatus("UNPAID")
        .build();
  }

  @Test
  void testCreate_Success() {
    // Given
    when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

    // When
    Invoice result = invoiceService.create(testInvoice);

    // Then
    assertNotNull(result);
    assertEquals(1, result.getInvoiceID());
    assertEquals(1725.00, result.getTotalAmount());
    assertEquals("UNPAID", result.getStatus());
    verify(invoiceRepository, times(1)).save(any(Invoice.class));
  }

  @Test
  void testRead_Success() {
    // Given
    when(invoiceRepository.findById(1)).thenReturn(Optional.of(testInvoice));

    // When
    Invoice result = invoiceService.read(1);

    // Then
    assertNotNull(result);
    assertEquals(1, result.getInvoiceID());
    assertEquals(1725.00, result.getTotalAmount());
    verify(invoiceRepository, times(1)).findById(1);
  }

  @Test
  void testRead_NotFound() {
    // Given
    when(invoiceRepository.findById(999)).thenReturn(Optional.empty());

    // When
    Invoice result = invoiceService.read(999);

    // Then
    assertNull(result);
    verify(invoiceRepository, times(1)).findById(999);
  }

  @Test
  void testRead_WithIntegerParameter() {
    // Given
    when(invoiceRepository.findById(1)).thenReturn(Optional.of(testInvoice));

    // When
    Invoice result = invoiceService.read(Integer.valueOf(1));

    // Then
    assertNotNull(result);
    assertEquals(1, result.getInvoiceID());
    verify(invoiceRepository, times(1)).findById(1);
  }

  @Test
  void testUpdate_Success() {
    // Given
    Invoice updatedInvoice = new Invoice.Builder()
        .setInvoiceID(1)
        .setPayment(testPayment)
        .setBooking(testBooking)
        .setIssueDate(testInvoice.getIssueDate())
        .setDueDate(testInvoice.getDueDate())
        .setSubTotal(1500.00)
        .setTaxAmount(225.00)
        .setTotalAmount(1725.00)
        .setStatus("PAID")
        .build();

    when(invoiceRepository.existsById(1)).thenReturn(true);
    when(invoiceRepository.save(any(Invoice.class))).thenReturn(updatedInvoice);

    // When
    Invoice result = invoiceService.update(updatedInvoice);

    // Then
    assertNotNull(result);
    assertEquals("PAID", result.getStatus());
    verify(invoiceRepository, times(1)).existsById(1);
    verify(invoiceRepository, times(1)).save(any(Invoice.class));
  }

  @Test
  void testUpdate_NotFound() {
    // Given
    when(invoiceRepository.existsById(999)).thenReturn(false);

    Invoice nonExistentInvoice = new Invoice.Builder()
        .setInvoiceID(999)
        .setPayment(testPayment)
        .setBooking(testBooking)
        .setIssueDate(LocalDateTime.now())
        .setDueDate(LocalDateTime.now().plusDays(30))
        .setSubTotal(1000.00)
        .setTaxAmount(150.00)
        .setTotalAmount(1150.00)
        .setStatus("UNPAID")
        .build();

    // When
    Invoice result = invoiceService.update(nonExistentInvoice);

    // Then
    assertNull(result);
    verify(invoiceRepository, times(1)).existsById(999);
    verify(invoiceRepository, never()).save(any());
  }

  @Test
  void testDelete_Success() {
    // Given
    doNothing().when(invoiceRepository).deleteById(1);

    // When
    invoiceService.delete(1);

    // Then
    verify(invoiceRepository, times(1)).deleteById(1);
  }

  @Test
  void testGetUserInvoices_Success() {
    // Given
    Invoice invoice2 = new Invoice.Builder()
        .setInvoiceID(2)
        .setPayment(testPayment)
        .setBooking(testBooking)
        .setIssueDate(LocalDateTime.now())
        .setDueDate(LocalDateTime.now().plusDays(30))
        .setSubTotal(2000.00)
        .setTaxAmount(300.00)
        .setTotalAmount(2300.00)
        .setStatus("PAID")
        .build();

    when(invoiceRepository.findByBooking_User_UserId(1)).thenReturn(Arrays.asList(testInvoice, invoice2));

    // When
    List<Invoice> result = invoiceService.getUserInvoices(1);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(1, result.get(0).getInvoiceID());
    assertEquals(2, result.get(1).getInvoiceID());
    verify(invoiceRepository, times(1)).findByBooking_User_UserId(1);
  }

  @Test
  void testGetUserInvoices_EmptyList() {
    // Given
    when(invoiceRepository.findByBooking_User_UserId(999)).thenReturn(Arrays.asList());

    // When
    List<Invoice> result = invoiceService.getUserInvoices(999);

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(invoiceRepository, times(1)).findByBooking_User_UserId(999);
  }

  @Test
  void testGetUserInvoices_ExceptionHandling() {
    // Given
    when(invoiceRepository.findByBooking_User_UserId(anyInt()))
        .thenThrow(new RuntimeException("Database error"));

    // When
    List<Invoice> result = invoiceService.getUserInvoices(1);

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty()); // Service returns empty list on error
    verify(invoiceRepository, times(1)).findByBooking_User_UserId(1);
  }

  @Test
  void testGetAllInvoices_Success() {
    // Given
    Invoice invoice2 = new Invoice.Builder()
        .setInvoiceID(2)
        .setPayment(testPayment)
        .setBooking(testBooking)
        .setIssueDate(LocalDateTime.now())
        .setDueDate(LocalDateTime.now().plusDays(30))
        .setSubTotal(2000.00)
        .setTaxAmount(300.00)
        .setTotalAmount(2300.00)
        .setStatus("PAID")
        .build();

    when(invoiceRepository.findAll()).thenReturn(Arrays.asList(testInvoice, invoice2));

    // When
    List<Invoice> result = invoiceService.getAllInvoices();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(invoiceRepository, times(1)).findAll();
  }

  @Test
  void testGetInvoicesByPayment_Success() {
    // Given
    when(invoiceRepository.findByPayment_PaymentID(1)).thenReturn(Arrays.asList(testInvoice));

    // When
    List<Invoice> result = invoiceService.getInvoicesByPayment(1);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1, result.get(0).getInvoiceID());
    verify(invoiceRepository, times(1)).findByPayment_PaymentID(1);
  }

  @Test
  void testGetInvoicesByPayment_EmptyList() {
    // Given
    when(invoiceRepository.findByPayment_PaymentID(999)).thenReturn(Arrays.asList());

    // When
    List<Invoice> result = invoiceService.getInvoicesByPayment(999);

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(invoiceRepository, times(1)).findByPayment_PaymentID(999);
  }

  @Test
  void testGetInvoicesByStatus_Success() {
    // Given
    when(invoiceRepository.findByStatus("UNPAID")).thenReturn(Arrays.asList(testInvoice));

    // When
    List<Invoice> result = invoiceService.getInvoicesByStatus("UNPAID");

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("UNPAID", result.get(0).getStatus());
    verify(invoiceRepository, times(1)).findByStatus("UNPAID");
  }

  @Test
  void testGetInvoicesByStatus_PaidStatus() {
    // Given
    Invoice paidInvoice = new Invoice.Builder()
        .setInvoiceID(2)
        .setPayment(testPayment)
        .setBooking(testBooking)
        .setIssueDate(LocalDateTime.now())
        .setDueDate(LocalDateTime.now().plusDays(30))
        .setSubTotal(2000.00)
        .setTaxAmount(300.00)
        .setTotalAmount(2300.00)
        .setStatus("PAID")
        .build();

    when(invoiceRepository.findByStatus("PAID")).thenReturn(Arrays.asList(paidInvoice));

    // When
    List<Invoice> result = invoiceService.getInvoicesByStatus("PAID");

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("PAID", result.get(0).getStatus());
    verify(invoiceRepository, times(1)).findByStatus("PAID");
  }

  @Test
  void testGetInvoices_Success() {
    // Given
    Invoice invoice2 = new Invoice.Builder()
        .setInvoiceID(2)
        .setPayment(testPayment)
        .setBooking(testBooking)
        .setIssueDate(LocalDateTime.now())
        .setDueDate(LocalDateTime.now().plusDays(30))
        .setSubTotal(2000.00)
        .setTaxAmount(300.00)
        .setTotalAmount(2300.00)
        .setStatus("PAID")
        .build();

    when(invoiceRepository.findAll()).thenReturn(Arrays.asList(testInvoice, invoice2));

    // When
    Set<Invoice> result = invoiceService.getInvoices();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.stream().anyMatch(inv -> inv.getInvoiceID() == 1));
    assertTrue(result.stream().anyMatch(inv -> inv.getInvoiceID() == 2));
    verify(invoiceRepository, times(1)).findAll();
  }

  @Test
  void testCalculations_TaxAndTotal() {
    // Test that the invoice calculations are correct
    double subTotal = testInvoice.getSubTotal();
    double taxAmount = testInvoice.getTaxAmount();
    double totalAmount = testInvoice.getTotalAmount();

    assertEquals(1500.00, subTotal);
    assertEquals(225.00, taxAmount);
    assertEquals(1725.00, totalAmount);
    assertEquals(subTotal + taxAmount, totalAmount, 0.01); // Delta for double comparison
  }

  @Test
  void testMultipleInvoicesForDifferentPayments() {
    // Given
    Payment payment2 = new Payment.Builder()
        .setPaymentID(2)
        .setBooking(testBooking)
        .setAmount(2000.00)
        .setPaymentMethod(PaymentMethod.CASH)
        .setPaymentStatus(PaymentStatus.PAID)
        .build();

    Invoice invoice2 = new Invoice.Builder()
        .setInvoiceID(2)
        .setPayment(payment2)
        .setBooking(testBooking)
        .setIssueDate(LocalDateTime.now())
        .setDueDate(LocalDateTime.now().plusDays(30))
        .setSubTotal(2000.00)
        .setTaxAmount(300.00)
        .setTotalAmount(2300.00)
        .setStatus("PAID")
        .build();

    when(invoiceRepository.findByPayment_PaymentID(1)).thenReturn(Arrays.asList(testInvoice));
    when(invoiceRepository.findByPayment_PaymentID(2)).thenReturn(Arrays.asList(invoice2));

    // When
    List<Invoice> payment1Invoices = invoiceService.getInvoicesByPayment(1);
    List<Invoice> payment2Invoices = invoiceService.getInvoicesByPayment(2);

    // Then
    assertEquals(1, payment1Invoices.size());
    assertEquals(1, payment2Invoices.size());
    assertEquals(1, payment1Invoices.get(0).getInvoiceID());
    assertEquals(2, payment2Invoices.get(0).getInvoiceID());
  }
}
