package za.co.carhire.service.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.service.IService;

import java.util.Set;

public interface ISupportTicketService extends IService<SupportTicket, Integer> {
    Set<SupportTicket> getTickets();
    SupportTicket create(SupportTicket supportTicket);
    SupportTicket read(int ticketID);
    SupportTicket update(SupportTicket supportTicket);
    void delete(int ticketID);
}
