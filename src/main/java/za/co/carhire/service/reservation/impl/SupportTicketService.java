package za.co.carhire.service.reservation.impl;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 09 October 2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.domain.reservation.TicketStatus;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.reservation.ISupportTicketRepository;
import za.co.carhire.service.reservation.ISupportTicketService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SupportTicketService implements ISupportTicketService {

    @Autowired
    private ISupportTicketRepository supportTicketRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public SupportTicket create(SupportTicket supportTicket) {
        return supportTicketRepository.save(supportTicket);
    }

    @Override
    public SupportTicket read(Integer ticketID) {
        return supportTicketRepository.findById(ticketID).orElse(null);
    }

    @Override
    public SupportTicket update(SupportTicket supportTicket) {
        if (supportTicketRepository.existsById(supportTicket.getTicketID())) {
            return supportTicketRepository.save(supportTicket);
        }
        return null;
    }

    @Override
    public void delete(Integer ticketID) {
        supportTicketRepository.deleteById(ticketID);
    }

    @Override
    public Set<SupportTicket> getTickets() {
        return supportTicketRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Set<SupportTicket> getTicketsByUser(Integer userId) {
        return supportTicketRepository.findByUser_UserId(userId).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<SupportTicket> getTicketsByStatus(TicketStatus status) {
        return supportTicketRepository.findByStatus(status).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<SupportTicket> getTicketsByBooking(Integer bookingId) {
        return supportTicketRepository.findByBooking_BookingID(bookingId).stream().collect(Collectors.toSet());
    }

    @Override
    public SupportTicket updateStatus(Integer ticketID, TicketStatus status) {
        SupportTicket ticket = supportTicketRepository.findById(ticketID).orElse(null);
        if (ticket != null) {
            ticket.setStatus(status);
            if (status == TicketStatus.RESOLVED || status == TicketStatus.CLOSED) {
                ticket.setResolvedAt(LocalDateTime.now());
            }
            return supportTicketRepository.save(ticket);
        }
        return null;
    }

    @Override
    public SupportTicket assignTicket(Integer ticketID, Integer assignedToUserId) {
        SupportTicket ticket = supportTicketRepository.findById(ticketID).orElse(null);
        if (ticket != null) {
            User assignedTo = userRepository.findById(assignedToUserId).orElse(null);
            if (assignedTo != null) {
                SupportTicket updatedTicket = new SupportTicket.Builder()
                        .copy(ticket)
                        .setAssignedTo(assignedTo)
                        .setStatus(TicketStatus.IN_PROGRESS)
                        .build();
                return supportTicketRepository.save(updatedTicket);
            }
        }
        return null;
    }

    @Override
    public SupportTicket resolveTicket(Integer ticketID) {
        return updateStatus(ticketID, TicketStatus.RESOLVED);
    }
}
