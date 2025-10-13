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
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.dto.reservation.InvoiceDTO;
import za.co.carhire.factory.reservation.InvoiceFactory;
import za.co.carhire.repository.reservation.IInvoiceRepository;
import za.co.carhire.service.reservation.IPaymentService;
import za.co.carhire.service.reservation.impl.InvoiceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5173"})
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private IPaymentService paymentService;

    // Keep this for backward compatibility with your current frontend
    @GetMapping("/read/{id}")
    public ResponseEntity<Invoice> read(@PathVariable int id) {
        System.out.println("Fetching invoice with ID: " + id);
        Invoice invoice = invoiceService.read(id);
        if (invoice != null) {
            System.out.println("Invoice found: " + invoice.getInvoiceID());
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            System.out.println("Invoice not found: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Also add the standard REST endpoint
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable int id) {
        return read(id); // Reuse the same method
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvoiceDTO>> getUserInvoices(@PathVariable int userId) {
        System.out.println("Fetching invoices for user: " + userId);
        List<Invoice> invoices = invoiceService.getUserInvoices(userId);
        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(InvoiceDTO::new)
                .collect(Collectors.toList());
        System.out.println("Found " + invoiceDTOs.size() + " invoices for user " + userId);
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        System.out.println("Fetching all invoices");
        List<Invoice> invoices = invoiceService.getAllInvoices();
        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(InvoiceDTO::new)
                .collect(Collectors.toList());
        System.out.println("Found " + invoiceDTOs.size() + " total invoices");
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByPayment(@PathVariable int paymentId) {
        System.out.println("Fetching invoices for payment: " + paymentId);
        List<Invoice> invoices = invoiceService.getInvoicesByPayment(paymentId);
        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(InvoiceDTO::new)
                .collect(Collectors.toList());
        System.out.println("Found " + invoiceDTOs.size() + " invoices for payment " + paymentId);
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceCreationRequest request) {
        try {
            System.out.println("Creating invoice for payment: " + request.getPaymentId());

            // Get payment and booking
            Payment payment = paymentService.read(request.getPaymentId());
            Booking booking = payment.getBooking();

            // Generate invoice
            Invoice invoice = InvoiceFactory.generateInvoice(payment, booking);

            if (invoice != null) {
                Invoice savedInvoice = invoiceService.create(invoice);
                System.out.println("Invoice created successfully: " + savedInvoice.getInvoiceID());
                return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
            } else {
                System.out.println("Invoice creation failed - invalid data");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println("Error creating invoice: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add this inner class for the request
    public static class InvoiceCreationRequest {
        private int paymentId;

        public int getPaymentId() { return paymentId; }
        public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    }
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