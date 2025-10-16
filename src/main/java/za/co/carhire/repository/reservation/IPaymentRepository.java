package za.co.carhire.repository.reservation;

/* IPaymentRepository.java
 * Sanele Zondi (221602011)
 * Due Date: 25/05/2025
 * Updated: Migrated from Stripe to PayFast
 * */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByBooking(Booking booking);
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);
    Optional<Payment> findById(int paymentID);
    Optional<Payment> findByPayfastPaymentId(String payfastPaymentId);
    Optional<Payment> findByBooking_BookingID(int bookingId);
    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
}