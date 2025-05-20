package za.co.carhire.domain.reservation;

/*Payment.java
 * Payment POJO class
 * Sanele Zondi (221602011)
 * Due Date: 11/05/2025
 * */

public class Payment {
    private int paymentID;
    private Booking booking;
    private double amount;
    private String paymentMethod;



    private Payment(){}
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
        private int paymentID;
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
