package za.co.carhire.service.reservation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.PaymentStatus;
import za.co.carhire.factory.reservation.InvoiceFactory;
import za.co.carhire.factory.reservation.PaymentFactory;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.repository.reservation.IPaymentRepository;
import za.co.carhire.service.reservation.IInvoiceService;
import za.co.carhire.service.reservation.IPaymentService;

import java.util.Optional;
import java.util.Set;

import static za.co.carhire.factory.reservation.InvoiceFactory.generateInvoice;

@Service
@Transactional
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IInvoiceService invoiceService;

    @Override
    public Set<Payment> getPayments() {
        return Set.of();
    }

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(Integer paymentID) {
        return paymentRepository.findById(paymentID).orElse(null);
    }

    @Override
    public Payment update(Payment payment) {
        if (paymentRepository.existsById(payment.getPaymentID())) {
            return paymentRepository.save(payment);
        }
        return null;
    }

    public Payment updatePaymentStatus(int paymentId, PaymentStatus status) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }

        Payment payment = paymentOpt.get();
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(int paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}