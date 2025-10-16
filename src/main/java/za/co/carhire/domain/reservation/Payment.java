package za.co.carhire.domain.reservation;

/*Payment.java
 * Payment POJO class
 * Sanele Zondi (221602011)
 * Due Date: 11/05/2025
 * Updated: Added PayFast integration fields and cascade configuration (South African payment gateway)
 * */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    @JsonIgnoreProperties({ "payment", "pickupLocation", "dropOffLocation", "user", "car" })
    private Booking booking;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 50)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    // PayFast-specific fields
    @Column(name = "payfast_payment_id", unique = true, length = 255)
    private String payfastPaymentId;

    @Column(name = "payfast_transaction_id", length = 255)
    private String payfastTransactionId;

    @Column(name = "merchant_id", length = 255)
    private String merchantId;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "currency", length = 3)
    private String currency = "ZAR";

    @Column(name = "transaction_reference", length = 255)
    private String transactionReference;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Column(name = "refund_amount")
    private Double refundAmount;

    @Column(name = "refund_date")
    private LocalDateTime refundDate;

    // Bidirectional relationship with Invoice (fixes orphan issue)
    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "payment", "booking" })
    private Invoice invoice;

    protected Payment() {
    }

    private Payment(Builder builder) {
        this.paymentID = builder.paymentID;
        this.booking = builder.booking;
        this.amount = builder.amount;
        this.paymentMethod = builder.paymentMethod;
        this.paymentStatus = builder.paymentStatus;
        this.payfastPaymentId = builder.payfastPaymentId;
        this.payfastTransactionId = builder.payfastTransactionId;
        this.merchantId = builder.merchantId;
        this.paymentDate = builder.paymentDate;
        this.currency = builder.currency;
        this.transactionReference = builder.transactionReference;
        this.failureReason = builder.failureReason;
        this.refundAmount = builder.refundAmount;
        this.refundDate = builder.refundDate;
        this.invoice = builder.invoice;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPayfastPaymentId() {
        return payfastPaymentId;
    }

    public void setPayfastPaymentId(String payfastPaymentId) {
        this.payfastPaymentId = payfastPaymentId;
    }

    public String getPayfastTransactionId() {
        return payfastTransactionId;
    }

    public void setPayfastTransactionId(String payfastTransactionId) {
        this.payfastTransactionId = payfastTransactionId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public LocalDateTime getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", payfastPaymentId='" + payfastPaymentId + '\'' +
                ", paymentDate=" + paymentDate +
                ", currency='" + currency + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                '}';
    }

    public static class Builder {
        private int paymentID;
        private Booking booking;
        private double amount;
        private PaymentMethod paymentMethod;
        private PaymentStatus paymentStatus;
        private String payfastPaymentId;
        private String payfastTransactionId;
        private String merchantId;
        private LocalDateTime paymentDate;
        private String currency = "ZAR";
        private String transactionReference;
        private String failureReason;
        private Double refundAmount;
        private LocalDateTime refundDate;
        private Invoice invoice;

        public Builder setPaymentID(int paymentID) {
            this.paymentID = paymentID;
            return this;
        }

        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder setPayfastPaymentId(String payfastPaymentId) {
            this.payfastPaymentId = payfastPaymentId;
            return this;
        }

        public Builder setPayfastTransactionId(String payfastTransactionId) {
            this.payfastTransactionId = payfastTransactionId;
            return this;
        }

        public Builder setMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder setTransactionReference(String transactionReference) {
            this.transactionReference = transactionReference;
            return this;
        }

        public Builder setFailureReason(String failureReason) {
            this.failureReason = failureReason;
            return this;
        }

        public Builder setRefundAmount(Double refundAmount) {
            this.refundAmount = refundAmount;
            return this;
        }

        public Builder setRefundDate(LocalDateTime refundDate) {
            this.refundDate = refundDate;
            return this;
        }

        public Builder setInvoice(Invoice invoice) {
            this.invoice = invoice;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentID = payment.getPaymentID();
            this.booking = payment.getBooking();
            this.amount = payment.getAmount();
            this.paymentMethod = payment.getPaymentMethod();
            this.paymentStatus = payment.getPaymentStatus();
            this.payfastPaymentId = payment.getPayfastPaymentId();
            this.payfastTransactionId = payment.getPayfastTransactionId();
            this.merchantId = payment.getMerchantId();
            this.paymentDate = payment.getPaymentDate();
            this.currency = payment.getCurrency();
            this.transactionReference = payment.getTransactionReference();
            this.failureReason = payment.getFailureReason();
            this.refundAmount = payment.getRefundAmount();
            this.refundDate = payment.getRefundDate();
            this.invoice = payment.getInvoice();
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}