package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Invoice;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Payment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByBooking(Booking booking);
    List<Invoice> findByPayment(Payment payment);
    List<Invoice> findByStatus(String status);
    List<Invoice> findByDueDateBeforeAndStatusNot(LocalDateTime date, String status);
    List<Invoice> findByTotalAmountGreaterThanEqual(double amount);
}