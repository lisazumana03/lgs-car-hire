package za.co.carhire.service.reservation.impl;

/* InvoiceService.java
 * Sanele Zondi (221602011)
 * Due Date: 25/05/2025
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.*;
import za.co.carhire.repository.reservation.IInvoiceRepository;
import za.co.carhire.service.reservation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService implements IInvoiceService {

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public Set<Invoice> getInvoices() {
        return Set.copyOf(invoiceRepository.findAll());
    }


    @Override
    @Transactional
    public Invoice create(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice read(Integer invoiceID) {
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceID);
        return invoice.orElse(null);
    }

    public Invoice read(int invoiceID) {
        return read(Integer.valueOf(invoiceID));
    }


    @Override
    @Transactional
    public Invoice update(Invoice invoice) {
        if (invoiceRepository.existsById(invoice.getInvoiceID())) {
            return invoiceRepository.save(invoice);
        }
        return null;
    }
    @Override
    @Transactional
    public void delete(int invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    @Transactional(readOnly = true)
    public List<Invoice> getUserInvoices(int userId) {
        try {
            return invoiceRepository.findByBooking_User_UserId(userId);
        } catch (Exception e) {
            System.err.println("Error fetching user invoices: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list instead of throwing
        }
    }

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByPayment(int paymentId) {
        return invoiceRepository.findByPayment_PaymentID(paymentId);
    }

    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }
}