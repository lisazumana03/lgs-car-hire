package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.service.IService;

import java.util.List;

public interface IPaymentService extends IService<Payment, Integer> {
    List<Payment> getPaymentsByBooking(Integer bookingId);
    List<Payment> getPaymentsByMethod(String paymentMethod);
    Payment createPayment(Integer bookingId, double amount, String method);
    Payment processRefund(Integer paymentId);
    boolean validatePayment(Integer bookingId, double amount, String method);
    List<Payment> getPaymentsInAmountRange(double minAmount, double maxAmount);
}