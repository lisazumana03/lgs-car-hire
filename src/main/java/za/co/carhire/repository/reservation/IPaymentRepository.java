package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.reservation.Booking;

import java.util.List;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByBooking(Booking booking);
    List<Payment> findByPaymentMethod(String paymentMethod);
    List<Payment> findByAmountGreaterThan(double amount);
    List<Payment> findByAmountBetween(double minAmount, double maxAmount);
    List<Payment> findByPaymentMethodIn(List<String> paymentMethods);
}