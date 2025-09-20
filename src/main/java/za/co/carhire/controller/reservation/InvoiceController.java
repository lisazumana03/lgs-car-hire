package za.co.carhire.controller.reservation;

/*InvoiceController.java
 * Invoice Controller class
 * Sanele Zondi (221602011)
 * */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.repository.reservation.IInvoiceRepository;
import za.co.carhire.service.reservation.impl.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@CrossOrigin(origins = "*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private IInvoiceRepository invoiceRepository;

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

    @GetMapping("/all")
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

//    @GetMapping("/download/{id}")
//    public ResponseEntity<byte[]> downloadInvoice(@PathVariable int id) {
//            Invoice invoice = invoiceService.read(id);
//            byte[] pdfBytes = generateInvoicePdf(invoice); // You need to implement this
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + id + ".pdf")
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(pdfBytes);
//    }
}