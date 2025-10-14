package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.dto.reservation.InvoiceDTO;
import za.co.carhire.factory.reservation.InvoiceFactory;
import za.co.carhire.service.reservation.IInvoiceService;
import za.co.carhire.service.reservation.IPaymentService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:3046", "http://127.0.0.1:3046" },
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5173"})
public class InvoiceController {

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IPaymentService paymentService;
    //invoice creation endpoint
    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceCreationRequest request) {
        try {
            System.out.println("Creating invoice for payment: " + request.getPaymentId());

            // Get payment using the service
            Payment payment = paymentService.read(request.getPaymentId());
            if (payment == null) {
                System.out.println("Payment not found: " + request.getPaymentId());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Booking booking = payment.getBooking();
            if (booking == null) {
                System.out.println("Booking not found for payment: " + request.getPaymentId());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            System.out.println("Found payment: " + payment.getPaymentID() + " and booking: " + booking.getBookingID());

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

    @GetMapping("/read/{id}")
    public ResponseEntity<InvoiceDTO> read(@PathVariable("id") int id) {
        try {
            System.out.println("Fetching invoice with ID: " + id);
            Invoice invoice = invoiceService.read(id);

            if (invoice != null) {
                System.out.println("Invoice found: " + invoice.getInvoiceID());

                try {
                    InvoiceDTO invoiceDTO = new InvoiceDTO(invoice);
                    System.out.println("DTO created successfully");
                    return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
                } catch (Exception dtoError) {
                    System.err.println("Error creating DTO: " + dtoError.getMessage());
                    dtoError.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                System.out.println("Invoice not found: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Unexpected error in read endpoint: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable int id) {
        return read(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvoiceDTO>> getUserInvoices(@PathVariable int userId) {
        try {
            System.out.println("=== GET USER INVOICES START ===");
            System.out.println("User ID: " + userId);

            List<Invoice> invoices = invoiceService.getUserInvoices(userId);
            System.out.println("Raw invoices count: " + invoices.size());

            List<InvoiceDTO> invoiceDTOs = invoices.stream()
                    .map(invoice -> {
                        try {
                            System.out.println("Creating DTO for invoice: " + invoice.getInvoiceID());
                            return new InvoiceDTO(invoice);
                        } catch (Exception e) {
                            System.err.println("Error creating DTO for invoice " + invoice.getInvoiceID() + ": " + e.getMessage());
                            // Create a fallback DTO
                            return createFallbackDTO(invoice);
                        }
                    })
                    .collect(Collectors.toList());

            System.out.println("Successfully created " + invoiceDTOs.size() + " DTOs");
            System.out.println("=== GET USER INVOICES END ===");

            return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in getUserInvoices: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private InvoiceDTO createFallbackDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setInvoiceID(invoice.getInvoiceID());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setSubTotal(invoice.getSubTotal());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setStatus(invoice.getStatus());
        dto.setIssueDate(invoice.getIssueDate());
        dto.setDueDate(invoice.getDueDate());
        dto.setCarModel("Vehicle");
        return dto;
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
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByPayment(@PathVariable("paymentId") int paymentId) {
        System.out.println("Fetching invoices for payment: " + paymentId);
        List<Invoice> invoices = invoiceService.getInvoicesByPayment(paymentId);
        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(InvoiceDTO::new)
                .collect(Collectors.toList());
        System.out.println("Found " + invoiceDTOs.size() + " invoices for payment " + paymentId);
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
    }

    // Debug endpoint
    @GetMapping("/debug/{id}")
    public ResponseEntity<String> debugInvoice(@PathVariable("id") int id) {
        try {
            System.out.println("Debugging invoice with ID: " + id);
            Invoice invoice = invoiceService.read(id);

            if (invoice != null) {
                String debugInfo = String.format(
                        "Invoice found - ID: %d, Status: %s, Amount: %.2f, Booking: %s, Payment: %s",
                        invoice.getInvoiceID(),
                        invoice.getStatus(),
                        invoice.getTotalAmount(),
                        invoice.getBooking() != null ? "Present" : "Null",
                        invoice.getPayment() != null ? "Present" : "Null"
                );
                System.out.println(debugInfo);
                return new ResponseEntity<>(debugInfo, HttpStatus.OK);
            } else {
                System.out.println("Invoice not found in database: " + id);
                return new ResponseEntity<>("Invoice not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("Error in debug endpoint: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Inner class for request
    public static class InvoiceCreationRequest {
        private int paymentId;

        public int getPaymentId() { return paymentId; }
        public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    }
}