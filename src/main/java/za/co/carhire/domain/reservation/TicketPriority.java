package za.co.carhire.domain.reservation;

public enum TicketPriority {
    LOW,            // Non-urgent, can wait
    MEDIUM,         // Normal priority
    HIGH,           // Important, needs attention soon
    URGENT,         // Critical, immediate attention required
    EMERGENCY       // System-critical or safety issue
}
