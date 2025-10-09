package za.co.carhire.domain.reservation;

/*
TicketStatus.java
Enum for Support Ticket Status
Date: 09 October 2025
 */

public enum TicketStatus {
    OPEN,           // Ticket just created
    IN_PROGRESS,    // Being handled by support
    RESOLVED,       // Issue resolved, waiting for customer confirmation
    CLOSED          // Ticket closed
}
