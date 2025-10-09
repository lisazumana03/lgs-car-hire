package za.co.carhire.service.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 09 October 2025
 */
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.domain.reservation.TicketStatus;
import za.co.carhire.service.IService;

import java.util.Set;

public interface ISupportTicketService extends IService<SupportTicket, Integer> {
    void delete(Integer ticketID);
    Set<SupportTicket> getTickets();
    Set<SupportTicket> getTicketsByUser(Integer userId);
    Set<SupportTicket> getTicketsByStatus(TicketStatus status);
    Set<SupportTicket> getTicketsByBooking(Integer bookingId);
    SupportTicket updateStatus(Integer ticketID, TicketStatus status);
    SupportTicket assignTicket(Integer ticketID, Integer assignedToUserId);
    SupportTicket resolveTicket(Integer ticketID);
}
