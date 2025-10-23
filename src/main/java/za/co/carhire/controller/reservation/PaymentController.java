package za.co.carhire.controller.reservation;

/*PaymentController.java
 * Payment Controller class
 * Sanele Zondi (221602011)
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.dto.reservation.PaymentRequest;
import za.co.carhire.service.reservation.impl.PaymentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5173" })
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-payment")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest request) {
        try {
            System.out.println("Creating payment for booking: " + request.getBookingId());
            Payment payment = paymentService.createPayment(
                    request.getBookingId(),
                    request.getAmount(),
                    request.getPaymentMethod());
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating payment: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.create(payment), HttpStatus.CREATED);
    }

    @PostMapping("/create/{bookingId}/{amount}/{method}")
    public ResponseEntity<Payment> createPayment(
            @PathVariable int bookingId,
            @PathVariable double amount,
            @PathVariable String method) {
        return new ResponseEntity<>(paymentService.createPayment(bookingId, amount, method), HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Payment> read(@PathVariable int id) {
        Payment payment = paymentService.read(id);
        if (payment != null) {
            return new ResponseEntity<>(payment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Payment> update(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.update(payment), HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}/{status}")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @PathVariable PaymentStatus status) {
        try {
            System.out.println("Received request to update payment " + id + " to status " + status);
            Payment updatedPayment = paymentService.updatePaymentStatus(id, status);
            if (updatedPayment != null) {
                // Return a simple response to avoid serialization issues with lazy-loaded
                // relationships
                return ResponseEntity.ok().body(Map.of(
                        "paymentID", updatedPayment.getPaymentID(),
                        "paymentStatus", updatedPayment.getPaymentStatus().toString(),
                        "amount", updatedPayment.getAmount(),
                        "message", "Payment status updated successfully"));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        paymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        try {
            System.out.println("INFO: Received payment verification request:");
            System.out.println("  - Booking ID: " + request.bookingId());
            System.out.println("  - Amount: " + request.amount());
            System.out.println("  - Payment Method: " + request.paymentMethod());
            System.out.println("  - Reference: " + request.reference());

            if (request.bookingId() == 0) {
                System.err.println("ERROR: Invalid booking ID (0). Cannot create payment.");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Booking ID is required and cannot be 0"));
            }

            Payment payment = paymentService.verifyAndCreatePayment(
                    request.bookingId(),
                    request.amount(),
                    request.paymentMethod(),
                    request.reference());

            System.out.println("SUCCESS: Payment created with ID: " + payment.getPaymentID());
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("ERROR: Error verifying payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllPayments() {
        try {
            System.out.println("=== PAYMENT CONTROLLER DEBUG ===");
            Set<Payment> payments = paymentService.getPayments();
            System.out.println("Payments service returned: " + payments.size() + " payments");

            // Convert to simple Map to avoid serialization issues
            List<Map<String, Object>> paymentData = payments.stream()
                    .map(payment -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("paymentID", payment.getPaymentID());
                        data.put("amount", payment.getAmount());
                        data.put("paymentMethod", payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null);
                        data.put("paymentStatus", payment.getPaymentStatus() != null ? payment.getPaymentStatus().name() : null);


                        // Safely handle booking relationship
                        if (payment.getBooking() != null) {
                            data.put("booking", Map.of(
                                    "bookingID", payment.getBooking().getBookingID()
                            ));

                            // Safely handle user relationship
                            if (payment.getBooking().getUser() != null) {
                                data.put("user", Map.of(
                                        "firstName", payment.getBooking().getUser().getFirstName(),
                                        "lastName", payment.getBooking().getUser().getLastName()
                                ));
                            }
                        }

                        return data;
                    })
                    .collect(Collectors.toList());

            System.out.println("Returning " + paymentData.size() + " payment records");
            return ResponseEntity.ok(paymentData);
        } catch (Exception e) {
            System.err.println("ERROR in getAllPayments: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DTO for payment verification
    public record PaymentVerificationRequest(
            int bookingId,
            double amount,
            String paymentMethod,
            String reference) {
    }


}