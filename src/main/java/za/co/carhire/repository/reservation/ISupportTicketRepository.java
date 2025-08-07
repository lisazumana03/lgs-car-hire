package za.co.carhire.repository.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */
import org.springframework.data.jpa.repository.JpaRepository;
import za.co.carhire.domain.reservation.SupportTicket;

import java.util.List;
import java.util.Optional;

public interface ISupportTicketRepository extends JpaRepository<SupportTicket, Integer> {
    Optional<SupportTicket> findById(int ticketID);
    List<SupportTicket> findByStatus(String status);
    List<SupportTicket> findAll();
}
