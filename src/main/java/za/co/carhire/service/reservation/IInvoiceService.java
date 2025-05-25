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
    Set<Invoice> getInvoices();
    Invoice create(Invoice invoice);
    Invoice read(int invoiceID);
    Invoice update(Invoice invoice);
    void delete(int invoiceID);
}