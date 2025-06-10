package za.co.carhire.factory.reservation;

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.SupportTicket;

import static org.junit.jupiter.api.Assertions.*;

public class SupportTicketFactoryTest {

    @Test
    public void testSubmitTicketValid() {
        SupportTicket ticket = SupportTicketFactory.submitTicket(100, 200, "Car engine light is on.");

        assertNotNull(ticket);
        assertEquals(100, ticket.getTicketID());
        assertEquals("Open", ticket.getStatus());
        assertEquals(0, ticket.getResponse());
        assertNotNull(ticket.getCreatedAT());
    }

    @Test
    public void testSubmitTicketInvalid() {
        SupportTicket ticket = SupportTicketFactory.submitTicket(-1, 200, "Message"); // Invalid ID
        assertNull(ticket);
    }

//    @Test
//    public void testCloseTicketValid() {
//        SupportTicket openTicket = SupportTicketFactory.submitTicket(100, 200, "Test issue.");
//        SupportTicket closedTicket = SupportTicketFactory.closeTicket(openTicket);
//
//        assertNotNull(closedTicket);
//        assertEquals("Closed", closedTicket.getStatus());
//        assertEquals(openTicket.getTicketID(), closedTicket.getTicketID());
//    }
//
//    @Test
//    public void testCloseTicketInvalid() {
//        SupportTicket closedTicket = SupportTicketFactory.closeTicket(null);
//        assertNull(closedTicket);
//    }
}