package za.co.carhire.domain.reservation;

/*Payment.java
 * Payment POJO class
 * Sanele Zondi (221602011)
 * Due Date: 11/05/2025
 * */

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentID;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    @Column(nullable = false)
    private double amount;
    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;



    protected Payment(){}
    private Payment(Builder builder) {
        this.paymentID = builder.paymentID;
        this.booking = builder.booking;
        this.amount = builder.amount;
        this.paymentMethod = builder.paymentMethod;
    }

    public int getPaymentID() {
        return paymentID;
    }
    public Booking getBooking() {
        return booking;
    }
    public double getAmount() {
        return amount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID='" + paymentID + '\'' +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
    public static class Builder {
        private Integer paymentID;
        private Booking booking;
        private double amount;
        private String paymentMethod;

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
        public Builder setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }
        public Builder copy (Payment payment) {
            this.paymentID = payment.getPaymentID();
            this.booking = payment.getBooking();
            this.amount = payment.getAmount();
            this.paymentMethod = payment.getPaymentMethod();
            return this;
        }
        public Payment build() {
            return new Payment(this);
        }
    }

}
