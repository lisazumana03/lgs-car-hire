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
import za.co.carhire.service.reservation.impl.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

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