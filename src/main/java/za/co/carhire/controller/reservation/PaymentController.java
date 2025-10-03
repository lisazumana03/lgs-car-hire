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

@RestController
@RequestMapping("/api/payment") // Added /api prefix
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.create(payment), HttpStatus.CREATED);
    }

    // Simple payment creation endpoint
    @PostMapping("/create-payment")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            Payment payment = paymentService.createPayment(
                    paymentRequest.getBookingId(),
                    paymentRequest.getAmount(),
                    paymentRequest.getPaymentMethod()
            );
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<Payment> updateStatus(
            @PathVariable int id,
            @PathVariable PaymentStatus status) {
        return new ResponseEntity<>(paymentService.updatePaymentStatus(id, status), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        paymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}