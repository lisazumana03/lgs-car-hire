package za.co.carhire.domain.reservation;

public enum TicketCategory {
    BOOKING_ISSUE,          // Problems with bookings
    VEHICLE_PROBLEM,        // Car issues during rental
    PAYMENT_ISSUE,          // Payment or billing problems
    ACCOUNT_ISSUE,          // User account problems
    TECHNICAL_SUPPORT,      // Website/app technical issues
    FEEDBACK,               // General feedback
    COMPLAINT,              // Complaints about service
    FEATURE_REQUEST,        // Suggestions for improvements
    DAMAGE_REPORT,          // Vehicle damage reporting
    ACCIDENT_REPORT,        // Accident during rental
    LOST_PROPERTY,          // Lost items in vehicle
    INSURANCE_QUERY,        // Insurance-related questions
    OTHER                   // Miscellaneous
}
