package za.co.carhire.controller.reservation;

/*InvoiceController.java
 * Invoice Controller class
 * Sanele Zondi (221602011)
 * */

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

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
public class InvoiceController {

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceCreationRequest request) {
        try {
            // Check if invoice already exists for this payment
            List<Invoice> existingInvoices = invoiceService.getInvoicesByPayment(request.getPaymentId());
            if (!existingInvoices.isEmpty()) {
                return new ResponseEntity<>(existingInvoices.get(0), HttpStatus.OK);
            }

            // Get payment using the service
            Payment payment = paymentService.read(request.getPaymentId());
            if (payment == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Booking booking = payment.getBooking();
            if (booking == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Generate invoice
            Invoice invoice = InvoiceFactory.generateInvoice(payment, booking);

            if (invoice != null) {
                Invoice savedInvoice = invoiceService.create(invoice);
                return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<InvoiceDTO> read(@PathVariable("id") int id) {
        try {
            Invoice invoice = invoiceService.read(id);
            if (invoice != null) {
                InvoiceDTO invoiceDTO = new InvoiceDTO(invoice);
                return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
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
            List<Invoice> invoices = invoiceService.getUserInvoices(userId);
            List<InvoiceDTO> invoiceDTOs = invoices.stream()
                    .map(invoice -> {
                        try {
                            return new InvoiceDTO(invoice);
                        } catch (Exception e) {
                            return createFallbackDTO(invoice);
                        }
                    })
                    .collect(Collectors.toList());
            return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
        } catch (Exception e) {
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
        dto.setCustomerName("Customer");
        dto.setCustomerEmail("customer@email.com");
        dto.setPickupLocationName("Pickup Location");
        dto.setPickupLocationAddress("Address not available");
        if (invoice.getBooking() != null) {
            dto.setBookingStartDate(invoice.getBooking().getStartDate());
            dto.setBookingEndDate(invoice.getBooking().getEndDate());
        }
        return dto;
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(InvoiceDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByPayment(@PathVariable("paymentId") int paymentId) {
        List<Invoice> invoices = invoiceService.getInvoicesByPayment(paymentId);
        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(InvoiceDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
    }

    @GetMapping("/debug/{id}")
    public ResponseEntity<String> debugInvoice(@PathVariable("id") int id) {
        try {
            Invoice invoice = invoiceService.read(id);
            if (invoice != null) {
                String debugInfo = String.format(
                        "Invoice found - ID: %d, Status: %s, Amount: %.2f, Booking: %s, Payment: %s",
                        invoice.getInvoiceID(),
                        invoice.getStatus(),
                        invoice.getTotalAmount(),
                        invoice.getBooking() != null ? "Present" : "Null",
                        invoice.getPayment() != null ? "Present" : "Null");
                return new ResponseEntity<>(debugInfo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invoice not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class InvoiceCreationRequest {
        private int paymentId;

        public int getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(int paymentId) {
            this.paymentId = paymentId;
        }
    }
}