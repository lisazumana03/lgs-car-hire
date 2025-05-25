package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.service.IService;

import java.time.LocalDateTime;
import java.util.List;

public interface IInvoiceService extends IService<Invoice, Integer> {
    List<Invoice> getInvoicesByBooking(Integer bookingId);
    List<Invoice> getInvoicesByStatus(String status);
    boolean updateInvoiceStatus(Integer invoiceId, String status);
    Invoice generateInvoice(Integer paymentId, Integer bookingId);
    List<Invoice> getOverdueInvoices();
    double calculateSubTotal(Integer bookingId);
    double calculateTaxAmount(Integer bookingId);
    double calculateTotalAmount(Integer bookingId);
}