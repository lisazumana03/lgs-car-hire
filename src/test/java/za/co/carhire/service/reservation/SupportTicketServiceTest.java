package za.co.carhire.service.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.feedback.Review;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.repository.reservation.ISupportTicketRepository;
import za.co.carhire.service.reservation.impl.SupportTicketService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SupportTicketServiceTest {
    private SupportTicketService service;
    private ISupportTicketRepository repository;

    private SupportTicket ticket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = new SupportTicket.Builder()
                .setTicketID(1)
                .setMessage("Cannot log into the system")
                .setStatus("PENDING")
                .setResponse(0)
                .build();
    }

    @Test
    void Create() {
        when(repository.save(ticket)).thenReturn(ticket);
        assertEquals(ticket, service.create(ticket));
    }

    @Test
    void Read() {
        when(repository.findById(1)).thenReturn(Optional.of(ticket));
        assertEquals(1, service.read(1));
    }

    @Test
    void Update() {
        when(repository.existsById(ticket.getTicketID())).thenReturn(true);
        when(repository.save(ticket)).thenReturn(ticket);
        assertEquals(ticket, service.update(ticket));
    }

    @Test
    void Delete() {
        service.delete(1);
        verify(repository).deleteById(1);
    }

    @Test
    void testGetAll() {
        List<SupportTicket> ticketList = Arrays.asList(ticket);
        when(repository.findAll()).thenReturn(ticketList);

        Set<SupportTicket> result = service.getTickets();
        assertEquals(1, result.size());
        assertTrue(result.contains(ticket));
}
}
