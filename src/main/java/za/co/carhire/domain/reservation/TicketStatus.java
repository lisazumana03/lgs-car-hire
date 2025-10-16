package za.co.carhire.domain.reservation;


public enum TicketStatus {
    OPEN,               // Newly created, awaiting review
    ASSIGNED,           // Assigned to staff member
    IN_PROGRESS,        // Being actively worked on
    WAITING_CUSTOMER,   // Waiting for customer response
    RESOLVED,           // Issue resolved, awaiting confirmation
    CLOSED,             // Ticket closed (resolved or cancelled)
    REOPENED,           // Previously closed ticket reopened
    CANCELLED           // Ticket cancelled by user
}
