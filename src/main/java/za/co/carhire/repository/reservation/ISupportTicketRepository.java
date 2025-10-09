package za.co.carhire.repository.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 09 October 2025
 */
import org.springframework.data.jpa.repository.JpaRepository;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.domain.reservation.TicketStatus;

import java.util.List;

public interface ISupportTicketRepository extends JpaRepository<SupportTicket, Integer> {
    List<SupportTicket> findByUser_UserId(Integer userId);
    List<SupportTicket> findByStatus(TicketStatus status);
    List<SupportTicket> findByBooking_BookingID(Integer bookingId);
    List<SupportTicket> findByAssignedTo_UserId(Integer assignedToUserId);
}
