//package za.co.carhire.controller.reservation;
//
/* InvoiceControllerTest.java
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import za.co.carhire.config.TestSecurityConfig;
//import za.co.carhire.domain.authentication.Role;
//import za.co.carhire.domain.authentication.User;
//import za.co.carhire.domain.reservation.Booking;
//import za.co.carhire.domain.reservation.BookingStatus;
//import za.co.carhire.domain.reservation.Invoice;
//import za.co.carhire.domain.reservation.Payment;
//import za.co.carhire.domain.reservation.PaymentMethod;
//import za.co.carhire.domain.reservation.PaymentStatus;
//import za.co.carhire.domain.vehicle.Car;
//import za.co.carhire.service.reservation.IInvoiceService;
//import za.co.carhire.service.reservation.IPaymentService;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(InvoiceController.class)
//@Import(TestSecurityConfig.class) // Use test security config
//class InvoiceControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @Autowired
//  private ObjectMapper objectMapper;
//
//  @MockBean
//  private IInvoiceService invoiceService;
//
//  @MockBean
//  private IPaymentService paymentService;
//
//  private Invoice testInvoice;
//  private Payment testPayment;
//  private Booking testBooking;
//  private User testUser;
//  private Car testCar;
//
//  @BeforeEach
//  void setUp() {
//    // Create test user
//    testUser = new User.Builder()
//            .setUserId(1)
//            .setFirstName("John")
//            .setLastName("Doe")
//            .setEmail("john.doe@example.com")
//            .setIdNumber(123456789L)
//            .setDateOfBirth(LocalDate.of(1990, 1, 1))
//            .setPhoneNumber("1234567890")
//            .setPassword("password")
//            .setRole(Role.CUSTOMER)
//            .build();
//
//    // Create test car
//    testCar = new Car.Builder()
//            .setCarID(1)
//            .setBrand("Toyota")
//            .setModel("Corolla")
//            .setYear(2023)
//            .setRentalPrice(500.00)
//            .setAvailability(true)
//            .build();
//
//    // Create test booking
//    testBooking = new Booking.Builder()
//            .setBookingID(1)
//            .setUser(testUser)
//            .setCar(testCar)
//            .setBookingDateAndTime(LocalDateTime.now())
//            .setStartDate(LocalDateTime.now().plusDays(1))
//            .setEndDate(LocalDateTime.now().plusDays(5))
//            .setBookingStatus(BookingStatus.CONFIRMED)
//            .build();
//
//    // Create test payment
//    testPayment = new Payment.Builder()
//            .setPaymentID(1)
//            .setBooking(testBooking)
//            .setAmount(1500.00)
//            .setPaymentMethod(PaymentMethod.CREDIT_CARD)
//            .setPaymentStatus(PaymentStatus.PAID)
//            .build();
//
//    // Set payment on booking
//    testBooking.setPayment(testPayment);
//
//    // Create test invoice
//    testInvoice = new Invoice.Builder()
//            .setInvoiceID(1)
//            .setPayment(testPayment)
//            .setBooking(testBooking)
//            .setIssueDate(LocalDateTime.now())
//            .setDueDate(LocalDateTime.now().plusDays(30))
//            .setSubTotal(1304.35)
//            .setTaxAmount(195.65)
//            .setTotalAmount(1500.00)
//            .setStatus("PAID")
//            .build();
//  }
//
//  @Test
//  void testCreateInvoice_Success() throws Exception {
//    // Given
//    when(paymentService.read(1)).thenReturn(testPayment);
//    when(invoiceService.getInvoicesByPayment(1)).thenReturn(Collections.emptyList());
//    when(invoiceService.create(any(Invoice.class))).thenReturn(testInvoice);
//
//    InvoiceController.InvoiceCreationRequest request = new InvoiceController.InvoiceCreationRequest();
//    request.setPaymentId(1);
//
//    // When & Then
//    mockMvc.perform(post("/api/invoice/create")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isCreated())
//            .andExpect(jsonPath("$.invoiceID").value(1))
//            .andExpect(jsonPath("$.totalAmount").value(1500.00));
//  }
//
//  @Test
//  void testCreateInvoice_AlreadyExists() throws Exception {
//    // Given
//    when(paymentService.read(1)).thenReturn(testPayment);
//    when(invoiceService.getInvoicesByPayment(1)).thenReturn(Arrays.asList(testInvoice));
//
//    InvoiceController.InvoiceCreationRequest request = new InvoiceController.InvoiceCreationRequest();
//    request.setPaymentId(1);
//
//    // When & Then
//    mockMvc.perform(post("/api/invoice/create")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.invoiceID").value(1));
//  }
//
//  @Test
//  void testCreateInvoice_PaymentNotFound() throws Exception {
//    // Given
//    when(paymentService.read(999)).thenReturn(null);
//
//    InvoiceController.InvoiceCreationRequest request = new InvoiceController.InvoiceCreationRequest();
//    request.setPaymentId(999);
//
//    // When & Then
//    mockMvc.perform(post("/api/invoice/create")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isNotFound());
//  }
//
//  @Test
//  void testReadInvoice_Success() throws Exception {
//    // Given
//    when(invoiceService.read(1)).thenReturn(testInvoice);
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/read/1"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.invoiceID").value(1))
//            .andExpect(jsonPath("$.totalAmount").value(1500.00))
//            .andExpect(jsonPath("$.status").value("PAID"));
//  }
//
//  @Test
//  void testReadInvoice_NotFound() throws Exception {
//    // Given
//    when(invoiceService.read(999)).thenReturn(null);
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/read/999"))
//            .andExpect(status().isNotFound());
//  }
//
//  @Test
//  void testGetUserInvoices_Success() throws Exception {
//    // Given
//    when(invoiceService.getUserInvoices(1)).thenReturn(Arrays.asList(testInvoice));
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/user/1"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.length()").value(1))
//            .andExpect(jsonPath("$[0].invoiceID").value(1));
//  }
//
//  @Test
//  void testGetUserInvoices_Empty() throws Exception {
//    // Given
//    when(invoiceService.getUserInvoices(999)).thenReturn(Collections.emptyList());
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/user/999"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.length()").value(0));
//  }
//
//  @Test
//  void testGetAllInvoices_Success() throws Exception {
//    // Given
//    when(invoiceService.getAllInvoices()).thenReturn(Arrays.asList(testInvoice));
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/all"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.length()").value(1))
//            .andExpect(jsonPath("$[0].invoiceID").value(1));
//  }
//
//  @Test
//  void testGetInvoicesByPayment_Success() throws Exception {
//    // Given
//    when(invoiceService.getInvoicesByPayment(1)).thenReturn(Arrays.asList(testInvoice));
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/payment/1"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.length()").value(1))
//            .andExpect(jsonPath("$[0].invoiceID").value(1));
//  }
//
//  @Test
//  void testDebugInvoice_Success() throws Exception {
//    // Given
//    when(invoiceService.read(1)).thenReturn(testInvoice);
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/debug/1"))
//            .andExpect(status().isOk())
//            .andExpect(content().string(org.hamcrest.Matchers.containsString("Invoice found")));
//  }
//
//  @Test
//  void testDebugInvoice_NotFound() throws Exception {
//    // Given
//    when(invoiceService.read(999)).thenReturn(null);
//
//    // When & Then
//    mockMvc.perform(get("/api/invoice/debug/999"))
//            .andExpect(status().isNotFound())
//            .andExpect(content().string("Invoice not found"));
//  }
//}