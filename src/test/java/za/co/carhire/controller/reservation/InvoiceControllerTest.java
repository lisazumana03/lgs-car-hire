package za.co.carhire.controller.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import za.co.carhire.config.AuthenticationService;
import za.co.carhire.config.JwtAuthenticationFilter;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentMethod;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.service.authentication.Impl.JwtService;
import za.co.carhire.service.reservation.IInvoiceService;
import za.co.carhire.service.reservation.IPaymentService;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * InvoiceControllerTest
 * 
 * Tests for InvoiceController with authorization checks
 * 
 * Author: Test Suite
 * Updated with comprehensive invoice testing
 */
@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private IInvoiceService invoiceService;

  @MockBean
  private IPaymentService paymentService;

  @MockBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private AuthenticationService authenticationService;

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
  @WithMockUser(roles = "CUSTOMER")
  void testCreateInvoice_Success() throws Exception {
    // Given
    when(paymentService.read(1)).thenReturn(testPayment);
    when(invoiceService.getInvoicesByPayment(1)).thenReturn(Arrays.asList());
    when(invoiceService.create(any(Invoice.class))).thenReturn(testInvoice);

    String requestBody = "{\"paymentId\": 1}";

    // When & Then
    mockMvc.perform(post("/api/invoice/create")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.invoiceID").value(1))
        .andExpect(jsonPath("$.totalAmount").value(1725.00))
        .andExpect(jsonPath("$.subTotal").value(1500.00))
        .andExpect(jsonPath("$.taxAmount").value(225.00))
        .andExpect(jsonPath("$.status").value("UNPAID"));
  }

  @Test
  void testCreateInvoice_Unauthenticated_Unauthorized() throws Exception {
    // Given
    String requestBody = "{\"paymentId\": 1}";

    // When & Then - Unauthenticated users cannot create invoices
    mockMvc.perform(post("/api/invoice/create")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testCreateInvoice_PaymentNotFound() throws Exception {
    // Given
    when(paymentService.read(999)).thenReturn(null);

    String requestBody = "{\"paymentId\": 999}";

    // When & Then
    mockMvc.perform(post("/api/invoice/create")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testReadInvoice_Success() throws Exception {
    // Given
    when(invoiceService.read(1)).thenReturn(testInvoice);

    // When & Then
    mockMvc.perform(get("/api/invoice/read/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invoiceID").value(1))
        .andExpect(jsonPath("$.totalAmount").value(1725.00))
        .andExpect(jsonPath("$.status").value("UNPAID"));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testReadInvoice_NotFound() throws Exception {
    // Given
    when(invoiceService.read(999)).thenReturn(null);

    // When & Then
    mockMvc.perform(get("/api/invoice/read/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testReadInvoice_Unauthenticated_Unauthorized() throws Exception {
    // When & Then
    mockMvc.perform(get("/api/invoice/read/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetInvoiceById_Success() throws Exception {
    // Given
    when(invoiceService.read(1)).thenReturn(testInvoice);

    // When & Then
    mockMvc.perform(get("/api/invoice/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invoiceID").value(1));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetUserInvoices_Success() throws Exception {
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

    when(invoiceService.getUserInvoices(1)).thenReturn(Arrays.asList(testInvoice, invoice2));

    // When & Then
    mockMvc.perform(get("/api/invoice/user/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].invoiceID").value(1))
        .andExpect(jsonPath("$[0].status").value("UNPAID"))
        .andExpect(jsonPath("$[1].invoiceID").value(2))
        .andExpect(jsonPath("$[1].status").value("PAID"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testGetAllInvoices_AsAdmin_Success() throws Exception {
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

    when(invoiceService.getAllInvoices()).thenReturn(Arrays.asList(testInvoice, invoice2));

    // When & Then
    mockMvc.perform(get("/api/invoice/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].totalAmount").value(1725.00))
        .andExpect(jsonPath("$[1].totalAmount").value(2300.00));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetInvoicesByPayment_Success() throws Exception {
    // Given
    when(invoiceService.getInvoicesByPayment(1)).thenReturn(Arrays.asList(testInvoice));

    // When & Then
    mockMvc.perform(get("/api/invoice/payment/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].invoiceID").value(1));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetUserInvoices_EmptyList() throws Exception {
    // Given
    when(invoiceService.getUserInvoices(999)).thenReturn(Arrays.asList());

    // When & Then
    mockMvc.perform(get("/api/invoice/user/999"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testCreateInvoice_AlreadyExists() throws Exception {
    // Given - Invoice already exists for this payment
    when(paymentService.read(1)).thenReturn(testPayment);
    when(invoiceService.getInvoicesByPayment(1)).thenReturn(Arrays.asList(testInvoice));

    String requestBody = "{\"paymentId\": 1}";

    // When & Then - Should return existing invoice with 200 OK
    mockMvc.perform(post("/api/invoice/create")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invoiceID").value(1));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testDebugInvoice_Success() throws Exception {
    // Given
    when(invoiceService.read(1)).thenReturn(testInvoice);

    // When & Then
    mockMvc.perform(get("/api/invoice/debug/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("Invoice found")));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testDebugInvoice_NotFound() throws Exception {
    // Given
    when(invoiceService.read(999)).thenReturn(null);

    // When & Then
    mockMvc.perform(get("/api/invoice/debug/999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Invoice not found in database: 999"));
  }
}
