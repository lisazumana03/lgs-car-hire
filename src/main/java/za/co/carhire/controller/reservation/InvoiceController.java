package za.co.carhire.controller.reservation;

/*InvoiceController.java
 * Invoice Controller class
 * Sanele Zondi (221602011)
 * */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.service.reservation.impl.InvoiceService;

@RestController
@RequestMapping("/invoice")
@CrossOrigin(origins = "*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        return new ResponseEntity<>(invoiceService.create(invoice), HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public Invoice read(@PathVariable int id) {
        return invoiceService.read(id);
    }

    @PutMapping("/update")
    public Invoice update(@RequestBody Invoice invoice) {
        return invoiceService.update(invoice);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Invoice> delete(@PathVariable int id) {
        invoiceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}