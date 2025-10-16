package za.co.carhire.controller.reservation;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentMethod;
import za.co.carhire.domain.reservation.PaymentStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/api/payment";
    private static int createdPaymentId;
    private static int createdBookingId;

    @Test
    @Order(1)
    void testCreatePayment_Success() {
        // First create a booking
        Booking booking = createTestBooking();

        // Then create payment for that booking
        String url = baseUrl + "/create/" + booking.getBookingID() + "/1500.00/CREDIT_CARD";

        ResponseEntity<Payment> response = restTemplate.postForEntity(url, null, Payment.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1500.00, response.getBody().getAmount());
        assertEquals(PaymentMethod.CREDIT_CARD, response.getBody().getPaymentMethod());

        createdPaymentId = response.getBody().getPaymentID();
        createdBookingId = booking.getBookingID();
    }

    @Test
    @Order(2)
    void testCreatePaymentWithRequestBody_Success() {
        String url = baseUrl + "/create";

        // Create payment data
        Map<String, Object> paymentData = new HashMap<>();
        paymentData.put("booking", Map.of("bookingID", createdBookingId));
        paymentData.put("amount", 2000.00);
        paymentData.put("paymentMethod", "DEBIT_CARD");
        paymentData.put("paymentStatus", "PENDING");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentData, headers);

        ResponseEntity<Payment> response = restTemplate.postForEntity(url, request, Payment.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(PaymentMethod.DEBIT_CARD, response.getBody().getPaymentMethod());
    }

    @Test
    @Order(3)
    void testReadPayment_Success() {
        String url = baseUrl + "/read/" + createdPaymentId;

        ResponseEntity<Payment> response = restTemplate.getForEntity(url, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdPaymentId, response.getBody().getPaymentID());
        assertEquals(PaymentMethod.CREDIT_CARD, response.getBody().getPaymentMethod());
    }

    @Test
    @Order(4)
    void testUpdatePaymentStatus() {
        String url = baseUrl + "/update-status/" + createdPaymentId + "/PAID";

        ResponseEntity<Payment> response = restTemplate.exchange(url, HttpMethod.PUT, null, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(PaymentStatus.COMPLETED, response.getBody().getPaymentStatus());
    }

    @Test
    @Order(5)
    void testDeletePayment() {
        String url = baseUrl + "/delete/" + createdPaymentId;

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Booking createTestBooking() {
        //assuming booking with ID 1 exists
        Booking booking = new Booking.Builder()
                .setBookingID(1)
                .setBookingStatus(BookingStatus.CONFIRMED)
                .setStartDate(LocalDateTime.now().plusDays(1))
                .setEndDate(LocalDateTime.now().plusDays(5))
                .build();

        return booking;
    }
}