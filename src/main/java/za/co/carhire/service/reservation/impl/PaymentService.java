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

import java.time.LocalDateTime;
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

        // Set payment date if not already set
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        // Set payment as PAID for PayFast payments with payment ID
        if (payment.getPaymentMethod() != null &&
                payment.getPaymentMethod().name().equalsIgnoreCase("PAYFAST") &&
                payment.getPayfastPaymentId() != null) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            System.out.println("Payment set to PAID status for PayFast payment");
        }

        Payment savedPayment = paymentRepository.save(payment);
        System.out.println("Payment saved with ID: " + savedPayment.getPaymentID() + " Status: " + savedPayment.getPaymentStatus());

        // Update the booking with the payment reference (bidirectional relationship)
        if (savedPayment.getBooking() != null) {
            Booking booking = savedPayment.getBooking();
            booking.setPayment(savedPayment);
            bookingRepository.save(booking);
            System.out.println("Booking updated with payment reference");

            // AUTOMATICALLY GENERATE INVOICE AFTER SUCCESSFUL PAYMENT
            if (savedPayment.getPaymentStatus() == PaymentStatus.PAID) {
                try {
                    System.out.println("Generating invoice for payment: " + savedPayment.getPaymentID());
                    Invoice invoice = InvoiceFactory.generateInvoice(savedPayment, booking);
                    if (invoice != null) {
                        // Set bidirectional relationship
                        invoice.setPayment(savedPayment);
                        savedPayment.setInvoice(invoice);

                        Invoice savedInvoice = invoiceService.create(invoice);
                        System.out.println("Invoice generated with ID: " + savedInvoice.getInvoiceID() + " Status: " + savedInvoice.getStatus());
                    } else {
                        System.out.println("Invoice generation returned null");
                    }
                } catch (Exception e) {
                    System.err.println("Error generating invoice: " + e.getMessage());
                    e.printStackTrace();
                    // Don't fail the entire payment transaction if invoice generation fails
                }
            }
        }

        return savedPayment;
    }

    @Transactional
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

        // Update payment date when status changes to PAID
        if (status == PaymentStatus.PAID && payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void delete(int paymentId) {
        // Invoice will be automatically deleted due to cascade = CascadeType.ALL in Payment entity
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            // Clear the bidirectional relationship with booking before deleting
            if (payment.getBooking() != null) {
                Booking booking = payment.getBooking();
                booking.setPayment(null);
                bookingRepository.save(booking);
            }
            paymentRepository.deleteById(paymentId);
        }
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
        payment.setTransactionReference(reference);
        payment.setPaymentDate(LocalDateTime.now());

        return create(payment);
    }

    /**
     * Create payment with PayFast payment ID
     */
    @Transactional
    public Payment createPayFastPayment(int bookingId, double amount, String payfastPaymentId, String merchantId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();

        // Check if booking already has a payment
        if (booking.getPayment() != null) {
            throw new RuntimeException("Booking already has a payment");
        }

        // Create payment with PayFast details
        Payment payment = PaymentFactory.createPayFastPayment(booking, amount, payfastPaymentId, merchantId);
        if (payment == null) {
            throw new RuntimeException("Invalid payment parameters");
        }

        return create(payment);
    }

    /**
     * Find payment by PayFast payment ID
     */
    public Optional<Payment> findByPayfastPaymentId(String payfastPaymentId) {
        return paymentRepository.findByPayfastPaymentId(payfastPaymentId);
    }

    /**
     * Process refund for a payment
     */
    @Transactional
    public Payment processRefund(int paymentId, double refundAmount) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }

        Payment payment = paymentOpt.get();

        if (payment.getPaymentStatus() != PaymentStatus.PAID) {
            throw new RuntimeException("Can only refund paid payments");
        }

        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        payment.setRefundAmount(refundAmount);
        payment.setRefundDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}
