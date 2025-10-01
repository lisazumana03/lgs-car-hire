package za.co.carhire.controller.reservation;
/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.service.reservation.ISupportTicketService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class SupportTicketControllerTest {

    @Mock
    private ISupportTicketService supportService;

    @InjectMocks
    private SupportTicketController supportController;

    private SupportTicket testTicket;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        User user = new User();
        testTicket = new SupportTicket.Builder()
                .setTicketID(1)
                .setUser(user)
                .setDescription("Cannot order the BMW on the systen")
                .setSubject("Booking error")
                .build();
    }

    @Test
    void create() {

        when(supportService.create(testTicket)).thenReturn(testTicket);

        SupportTicket result = supportController.create(testTicket).getBody();

        assertNotNull(result);
        assertEquals(testTicket, result);
        verify(supportService, times(1)).create(testTicket);
    }

    @Test
    void read() {

        int ticketID = 1;
        when(supportService.read(ticketID)).thenReturn(testTicket);

        SupportTicket result = supportController.create(null).getBody();

        assertNotNull(result);
        assertEquals(testTicket, result);
        verify(supportService, times(1)).read(ticketID);
    }

    @Test
    void update() {

        when(supportService.update(testTicket)).thenReturn(testTicket);

        SupportTicket result = supportController.update(testTicket).getBody();

        assertNotNull(result);
        assertEquals(testTicket, result);
        verify(supportService, times(1)).update(testTicket);
    }


    @Test
    void delete() {

        int ticketID = 1;
        doNothing().when(supportService).delete(ticketID);

        supportController.delete(ticketID);

        verify(supportService, times(1)).delete(ticketID);
    }
}
