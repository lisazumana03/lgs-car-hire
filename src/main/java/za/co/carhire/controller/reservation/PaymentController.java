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
import za.co.carhire.service.reservation.impl.PaymentService;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.create(payment), HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public Payment read(@PathVariable int id) {
        return paymentService.read(id);
    }

    @PutMapping("/update")
    public Payment update(@RequestBody Payment payment) {
        return paymentService.update(payment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Payment> delete(@PathVariable int id) {
        paymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}