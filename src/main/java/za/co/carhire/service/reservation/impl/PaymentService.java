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
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IInvoiceService invoiceService;

    @Override
    public Set<Payment> getPayments() {
        return Set.copyOf(paymentRepository.findAll());
    }

    @Override
    @Transactional
    public Payment create(Payment payment) {

        if (payment.getBooking() != null) {
            Optional<Booking> bookingOpt = bookingRepository.findById(payment.getBooking().getBookingID());
            if (bookingOpt.isEmpty()) {
                throw new RuntimeException("Booking not found");
            }

            Booking booking = bookingOpt.get();
            if (booking.getPayment() != null) {
                throw new RuntimeException("Booking already has a payment");
            }

            payment.setBooking(booking);
        }

        Payment savedPayment = paymentRepository.save(payment);

        // Update the booking with the payment reference
        if (savedPayment.getBooking() != null) {
            Booking booking = savedPayment.getBooking();
            booking.setPayment(savedPayment);
            bookingRepository.save(booking);

            // AUTOMATICALLY GENERATE INVOICE AFTER PAYMENT
            Invoice invoice = InvoiceFactory.generateInvoice(savedPayment, booking);
            if (invoice != null) {
                invoiceService.create(invoice);
            }
        }

        return savedPayment;
    }

    public Payment createPayment(int bookingId, double amount, String paymentMethod) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();

        // Check if booking already has a payment
        if (booking.getPayment() != null) {
            throw new RuntimeException("Booking already has a payment");
        }

        // Create payment using factory
        Payment payment = PaymentFactory.createPayment(booking, amount, paymentMethod);
        if (payment == null) {
            throw new RuntimeException("Invalid payment parameters");
        }

        return create(payment);
    }

    @Override
    public Payment read(Integer paymentID) {
        return paymentRepository.findById(paymentID).orElse(null);
    }

    @Override
    @Transactional
    public Payment update(Payment payment) {
        if (paymentRepository.existsById(payment.getPaymentID())) {
            return paymentRepository.save(payment);
        }
        return null;
    }

    @Transactional
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
    @Transactional
    public void delete(int paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    @Override
    public boolean verifyPayment(int paymentId) {
        return paymentRepository.existsById(paymentId);
    }

    @Transactional
    public Payment verifyAndCreatePayment(int bookingId, double amount, String paymentMethod, String reference) {
        System.out.println("INFO: PaymentService - Starting payment verification for booking " + bookingId);

        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            System.err.println("ERROR: Booking not found with ID: " + bookingId);
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();
        System.out.println("INFO: Found booking " + bookingId + " with status: " + booking.getBookingStatus());

        // Check if booking already has a payment
        if (booking.getPayment() != null) {
            System.out.println("INFO: Booking already has payment. Updating to COMPLETED");
            Payment existingPayment = booking.getPayment();
            existingPayment.setPaymentStatus(PaymentStatus.COMPLETED);
            Payment updated = paymentRepository.save(existingPayment);
            System.out.println("SUCCESS: Payment " + updated.getPaymentID() + " updated to COMPLETED");
            return updated;
        }

        System.out.println("INFO: Creating new payment for booking " + bookingId);
        // Create new payment with COMPLETED status
        Payment payment = PaymentFactory.createPayment(booking, amount, paymentMethod);
        if (payment == null) {
            System.err.println("ERROR: PaymentFactory returned null");
            throw new RuntimeException("Invalid payment parameters");
        }

        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        System.out.println("INFO: Saving payment to database...");
        Payment savedPayment = create(payment);
        System.out.println("SUCCESS: Payment saved with ID: " + savedPayment.getPaymentID());
        return savedPayment;
    }
}
