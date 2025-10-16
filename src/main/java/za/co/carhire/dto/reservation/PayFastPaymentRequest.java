package za.co.carhire.dto.reservation;

/**
 * DTO for PayFast payment requests
 * Updated: Migrated from Stripe to PayFast (South African payment gateway)
 */
public class PayFastPaymentRequest {
    private int bookingId;
    private double amount;
    private String payfastPaymentId;
    private String merchantId;
    private String currency;

    // Constructors
    public PayFastPaymentRequest() {
        this.currency = "ZAR";
    }

    public PayFastPaymentRequest(int bookingId, double amount, String payfastPaymentId, String merchantId) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.payfastPaymentId = payfastPaymentId;
        this.merchantId = merchantId;
        this.currency = "ZAR";
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPayfastPaymentId() {
        return payfastPaymentId;
    }

    public void setPayfastPaymentId(String payfastPaymentId) {
        this.payfastPaymentId = payfastPaymentId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
