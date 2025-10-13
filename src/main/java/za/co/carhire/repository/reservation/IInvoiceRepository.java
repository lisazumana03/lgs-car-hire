package za.co.carhire.repository.reservation;

/* IInvoiceRepository.java
 * Sanele Zondi (221602011)
 * Due Date: 25/05/2025
 * */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.*;

import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByBooking(Booking booking);
    List<Invoice> findByPayment(Payment payment);
    List<Invoice> findById(int invoiceID);
    List<Invoice> findByStatus(String status);
    List<Invoice> findByPayment_PaymentID(int paymentID);
    List<Invoice> findByBooking_User_UserId(int userId);
}