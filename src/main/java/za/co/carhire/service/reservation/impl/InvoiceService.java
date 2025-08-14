package za.co.carhire.service.reservation.impl;

/* InvoiceService.java
 * Sanele Zondi (221602011)
 * Due Date: 25/05/2025
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.repository.reservation.IInvoiceRepository;
import za.co.carhire.service.reservation.*;

import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService implements IInvoiceService {

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public Set<Invoice> getInvoices() {
        return Set.of();
    }

    @Override
    public Invoice create(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice read(Integer invoiceID) {
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceID);
        return invoice.orElse(null);
    }

    public Invoice read(int invoiceID) {
        return read(Integer.valueOf(invoiceID));
    }


    @Override
    public Invoice update(Invoice invoice) {
        if (invoiceRepository.existsById(invoice.getInvoiceID())) {
            return invoiceRepository.save(invoice);
        }
        return null;
    }
    @Override
    public void delete(int invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }
}