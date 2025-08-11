package za.co.carhire.service.reservation.impl;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.repository.reservation.ISupportTicketRepository;
import za.co.carhire.service.reservation.ISupportTicketService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SupportTicketService implements ISupportTicketService {

    @Autowired
    private ISupportTicketRepository supportTicketRepository;

    @Override
    public SupportTicket create(SupportTicket supportTicket) {
        return supportTicketRepository.save(supportTicket);
    }

    @Override

    public SupportTicket read(Integer ticketID) {
        Optional<SupportTicket> supportTicket = supportTicketRepository.findById(ticketID);
        return supportTicket.orElse(null);
    }

    public SupportTicket read(int ticketID) {
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
    public void delete(int ticketID) {
        supportTicketRepository.deleteById(ticketID);
    }

    @Override
    public Set<SupportTicket> getTickets() {
        return supportTicketRepository.findAll().stream().collect(Collectors.toSet());
    }
}
