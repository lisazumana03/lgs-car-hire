package za.co.carhire.domain.reservation;

public enum MaintenanceStatus {
    PENDING,      // Maintenance is pending/awaiting scheduling
    SCHEDULED,    // Maintenance is scheduled but not yet performed
    IN_PROGRESS,  // Maintenance is currently being performed
    COMPLETED,    // Maintenance has been completed successfully
    CANCELLED,    // Maintenance was cancelled
    DELAYED       // Maintenance was delayed/postponed
}
