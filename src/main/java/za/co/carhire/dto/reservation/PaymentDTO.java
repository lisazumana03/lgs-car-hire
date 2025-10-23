package za.co.carhire.dto.reservation;

import za.co.carhire.domain.reservation.Payment;

import java.time.LocalDateTime;

// Create this DTO class
public class PaymentDTO {
    private Integer paymentID;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime paymentDate;
    private Integer bookingId;
    private String customerName;

    // constructor, getters, and setters
    public PaymentDTO(Payment payment) {
        this.paymentID = payment.getPaymentID();
        this.amount = payment.getAmount();
        this.paymentMethod = payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null;
        this.paymentStatus = payment.getPaymentStatus() != null ? payment.getPaymentStatus().name() : null;

        if (payment.getBooking() != null) {
            this.bookingId = payment.getBooking().getBookingID();
            if (payment.getBooking().getUser() != null) {
                this.customerName = payment.getBooking().getUser().getFirstName() + " " +
                        payment.getBooking().getUser().getLastName();
            }
        }
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;

    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}