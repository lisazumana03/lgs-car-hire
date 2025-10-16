package za.co.carhire.mapper;

import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.dto.reservation.PaymentResponse;

/**
 * Mapper class for converting Payment entities to DTOs
 * Updated: Migrated from Stripe to PayFast
 */
public class PaymentMapper {

    public static PaymentResponse toResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponse response = new PaymentResponse();
        response.setPaymentID(payment.getPaymentID());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPayfastPaymentId(payment.getPayfastPaymentId());
        response.setPayfastTransactionId(payment.getPayfastTransactionId());
        response.setMerchantId(payment.getMerchantId());
        response.setPaymentDate(payment.getPaymentDate());
        response.setCurrency(payment.getCurrency());
        response.setTransactionReference(payment.getTransactionReference());
        response.setFailureReason(payment.getFailureReason());
        response.setRefundAmount(payment.getRefundAmount());
        response.setRefundDate(payment.getRefundDate());

        if (payment.getBooking() != null) {
            response.setBookingID(payment.getBooking().getBookingID());
        }

        return response;
    }
}
