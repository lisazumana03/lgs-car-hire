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
        System.out.println("Creating payment for booking: " +
                (payment.getBooking() != null ? payment.getBooking().getBookingID() : "null"));

        if (payment.getBooking() != null) {
            Optional<Booking> bookingOpt = bookingRepository.findById(payment.getBooking().getBookingID());
            if (bookingOpt.isEmpty()) {
                throw new RuntimeException("Booking not found");
            }

            Booking booking = bookingOpt.get();
            System.out.println("Found booking: " + booking.getBookingID());

            // Check if booking already has a payment
            if (booking.getPayment() != null) {
                System.out.println("Booking already has payment: " + booking.getPayment().getPaymentID());
                throw new RuntimeException("Booking already has a payment");
            }

            payment.setBooking(booking);
        }

        // Set payment as PAID immediately for Paystack payments
        if (payment.getPaymentMethod() != null &&
                payment.getPaymentMethod().name().equalsIgnoreCase("PAYSTACK")) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            System.out.println("Payment set to PAID status for Paystack payment");
        }

        Payment savedPayment = paymentRepository.save(payment);
        System.out.println("Payment saved with ID: " + savedPayment.getPaymentID() + " Status: " + savedPayment.getPaymentStatus());

        // Update the booking with the payment reference
        if (savedPayment.getBooking() != null) {
            Booking booking = savedPayment.getBooking();
            booking.setPayment(savedPayment);
            bookingRepository.save(booking);
            System.out.println("Booking updated with payment reference");

            // AUTOMATICALLY GENERATE INVOICE AFTER PAYMENT
            try {
                System.out.println("Generating invoice for payment: " + savedPayment.getPaymentID());
                Invoice invoice = InvoiceFactory.generateInvoice(savedPayment, booking);
                if (invoice != null) {
                    Invoice savedInvoice = invoiceService.create(invoice);
                    System.out.println("Invoice generated with ID: " + savedInvoice.getInvoiceID() + " Status: " + savedInvoice.getStatus());
                } else {
                    System.out.println("Invoice generation returned null");
                }
            } catch (Exception e) {
                System.err.println("Error generating invoice: " + e.getMessage());
                e.printStackTrace();
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

        // Set payment as PAID since we're verifying it
        payment.setPaymentStatus(PaymentStatus.PAID);

        return create(payment);
    }
}
