package za.co.carhire.factory.reservation;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.SupportTicket;

import static org.junit.jupiter.api.Assertions.*;

public class SupportTicketFactoryTest {

    @Test
    public void testSubmitTicketValid() {
        User user = new User();
        SupportTicket ticket = SupportTicketFactory.submitTicket(100, user, "Car engine light is on.");

        assertNotNull(ticket);
        assertEquals(100, ticket.getTicketID());
        assertEquals("Open", ticket.getStatus());
        assertEquals(0, ticket.getResponse());
    }

    @Test
    public void testSubmitTicketInvalid() {
        User user = new User();
        SupportTicket ticket = SupportTicketFactory.submitTicket(-1, user, "Car needs maintencance");
        assertNull(ticket);
    }

    @Test
    public void testCloseTicketValid() {

        SupportTicket closedTicket = SupportTicketFactory.closeTicket(250);

        assertNotNull(closedTicket);
        assertEquals("Closed", closedTicket.getStatus());
    }

    @Test
    public void testCloseTicketInvalid() {
        SupportTicket closedTicket = SupportTicketFactory.closeTicket(-43);
        assertNull(closedTicket);
    }
}