package za.co.carhire.dto.reservation;

/**
 * DTO for refund requests
 */
public class RefundRequest {
    private int paymentId;
    private double refundAmount;
    private String reason;

    // Constructors
    public RefundRequest() {}

    public RefundRequest(int paymentId, double refundAmount, String reason) {
        this.paymentId = paymentId;
        this.refundAmount = refundAmount;
        this.reason = reason;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
