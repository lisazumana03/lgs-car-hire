package za.co.carhire.service.reservation;

/* IInvoiceService.java
 * Sanele Zondi (221602011)
 * Due Date: 25/05/2025
 * */

import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.service.IService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IInvoiceService extends IService<Invoice, Integer> {
    List<Invoice> getUserInvoices(int userId);
    List<Invoice> getAllInvoices();
    List<Invoice> getInvoicesByPayment(int paymentId);
    List<Invoice> getInvoicesByStatus(String status);

    Set<Invoice> getInvoices();

    void delete(int invoiceID);
}