package za.co.carhire.controller.reservation;
/* PaymentControllerTest.java
 * Sanele Zondi (221602011)
 * Due Date: 18/05/2025
 * */
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.dto.reservation.PaymentRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/api/payment";
    private static int createdPaymentId;
    private static final int TEST_BOOKING_ID = 1;

    @Test
    @Order(1)
    void testCreatePaymentWithRequestBody_Success() {
        String url = baseUrl + "/create-payment";

        PaymentRequest request = new PaymentRequest(TEST_BOOKING_ID, 1500.00, "CREDIT_CARD");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Payment> response = restTemplate.postForEntity(url, requestEntity, Payment.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1500.00, response.getBody().getAmount());
        assertEquals("CREDIT_CARD", response.getBody().getPaymentMethod().name());

        createdPaymentId = response.getBody().getPaymentID();
    }

    @Test
    @Order(2)
    void testCreatePaymentWithPathVariables_Success() {
        String url = baseUrl + "/create/" + TEST_BOOKING_ID + "/2000.00/DEBIT_CARD";

        ResponseEntity<Payment> response = restTemplate.postForEntity(url, null, Payment.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2000.00, response.getBody().getAmount());
        assertEquals("DEBIT_CARD", response.getBody().getPaymentMethod().name());
    }

    @Test
    @Order(3)
    void testReadPayment_Success() {
        String url = baseUrl + "/read/" + createdPaymentId;

        ResponseEntity<Payment> response = restTemplate.getForEntity(url, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdPaymentId, response.getBody().getPaymentID());
    }

    @Test
    @Order(4)
    void testReadPayment_NotFound() {
        String url = baseUrl + "/read/99999";

        ResponseEntity<Payment> response = restTemplate.getForEntity(url, Payment.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    void testUpdatePaymentStatus_Success() {
        String url = baseUrl + "/update-status/" + createdPaymentId + "/PAID";

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, null, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Payment status updated successfully", response.getBody().get("message"));
    }

    @Test
    @Order(6)
    void testUpdatePaymentStatus_InvalidStatus() {
        String url = baseUrl + "/update-status/" + createdPaymentId + "/INVALID_STATUS";

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, null, Map.class);

        // This will likely return 400 Bad Request due to enum conversion
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    @Order(7)
    void testVerifyPayment_Success() {
        String url = baseUrl + "/verify";

        Map<String, Object> request = new HashMap<>();
        request.put("bookingId", TEST_BOOKING_ID);
        request.put("amount", 2500.00);
        request.put("paymentMethod", "PAYSTACK");
        request.put("reference", "REF12345");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Payment> response = restTemplate.postForEntity(url, requestEntity, Payment.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("PAYSTACK", response.getBody().getPaymentMethod().name());
    }

    @Test
    @Order(8)
    void testVerifyPayment_InvalidBookingId() {
        String url = baseUrl + "/verify";

        Map<String, Object> request = new HashMap<>();
        request.put("bookingId", 0); // Invalid booking ID
        request.put("amount", 2500.00);
        request.put("paymentMethod", "PAYSTACK");
        request.put("reference", "REF12345");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Booking ID is required"));
    }

    @Test
    @Order(9)
    void testDeletePayment() {
        String url = baseUrl + "/delete/" + createdPaymentId;

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}