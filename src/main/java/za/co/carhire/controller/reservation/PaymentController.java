package za.co.carhire.controller.reservation;

/*PaymentController.java
 * Payment Controller class
 * Sanele Zondi (221602011)
 * Updated: Added PayFast integration and proper DTO usage (South African payment gateway)
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.dto.reservation.*;
import za.co.carhire.mapper.PaymentMapper;
import za.co.carhire.service.reservation.impl.PaymentService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping({"/payment", "/api/payment"})
@CrossOrigin(origins = {"http://localhost:3046", "http://127.0.0.1:3046", "http://localhost:5173", "http://localhost:5173"})
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    /**
     * Create payment using standard payment method
     */
    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) {
        try {
            if (request.getBookingId() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid booking ID"));
            }

            if (request.getAmount() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid payment amount"));
            }

            System.out.println("Creating payment for booking: " + request.getBookingId());
            Payment payment = paymentService.createPayment(
                    request.getBookingId(),
                    request.getAmount(),
                    request.getPaymentMethod()
            );

            PaymentResponse response = PaymentMapper.toResponse(payment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Create PayFast payment with payment ID
     */
    @PostMapping("/create-payfast-payment")
    public ResponseEntity<?> createPayFastPayment(@RequestBody PayFastPaymentRequest request) {
        try {
            if (request.getBookingId() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid booking ID"));
            }

            if (request.getAmount() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid payment amount"));
            }

            if (request.getPayfastPaymentId() == null || request.getPayfastPaymentId().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "PayFast Payment ID is required"));
            }

            System.out.println("Creating PayFast payment for booking: " + request.getBookingId());
            Payment payment = paymentService.createPayFastPayment(
                    request.getBookingId(),
                    request.getAmount(),
                    request.getPayfastPaymentId(),
                    request.getMerchantId()
            );

            PaymentResponse response = PaymentMapper.toResponse(payment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating PayFast payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get payment by ID
     */
    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable int id) {
        try {
            Payment payment = paymentService.read(id);
            if (payment != null) {
                PaymentResponse response = PaymentMapper.toResponse(payment);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Payment not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get payment by PayFast payment ID
     */
    @GetMapping("/payfast/{payfastPaymentId}")
    public ResponseEntity<?> getByPayFastPaymentId(@PathVariable String payfastPaymentId) {
        try {
            Optional<Payment> paymentOpt = paymentService.findByPayfastPaymentId(payfastPaymentId);
            if (paymentOpt.isPresent()) {
                PaymentResponse response = PaymentMapper.toResponse(paymentOpt.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Payment not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Update payment status
     */
    @PutMapping("/update-status/{id}/{status}")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @PathVariable PaymentStatus status) {
        try {
            Payment payment = paymentService.updatePaymentStatus(id, status);
            PaymentResponse response = PaymentMapper.toResponse(payment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Process refund
     */
    @PostMapping("/refund")
    public ResponseEntity<?> processRefund(@RequestBody RefundRequest request) {
        try {
            if (request.getPaymentId() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid payment ID"));
            }

            if (request.getRefundAmount() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid refund amount"));
            }

            Payment payment = paymentService.processRefund(
                    request.getPaymentId(),
                    request.getRefundAmount()
            );

            PaymentResponse response = PaymentMapper.toResponse(payment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete payment
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            paymentService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Payment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Verify payment (legacy endpoint - kept for backward compatibility)
     */
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
            PaymentResponse response = PaymentMapper.toResponse(payment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("ERROR: Error verifying payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // DTO for payment verification (legacy)
    public record PaymentVerificationRequest(
            int bookingId,
            double amount,
            String paymentMethod,
            String reference) {
    }
}