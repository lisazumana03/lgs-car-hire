package za.co.carhire.factory.reservation;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.domain.reservation.TicketPriority;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
Updated: 09 October 2025
 */

public class SupportTicketFactory {

    /**
     * Create a new support ticket
     * @param user The user creating the ticket (required)
     * @param subject The ticket subject (required)
     * @param description The ticket description (optional)
     * @return A new SupportTicket or null if validation fails
     */
    public static SupportTicket createTicket(User user, String subject, String description){
        if(user == null || Helper.isEmptyOrNull(subject)){
            return null;
        }
        return new SupportTicket.Builder()
                .setUser(user)
                .setSubject(subject)
                .setDescription(description)
                .build();
    }

    /**
     * Create a new support ticket related to a booking
     * @param user The user creating the ticket (required)
     * @param booking The related booking (optional)
     * @param subject The ticket subject (required)
     * @param description The ticket description (optional)
     * @param priority The ticket priority (optional, defaults to MEDIUM)
     * @return A new SupportTicket or null if validation fails
     */
    public static SupportTicket createTicketWithBooking(User user, Booking booking, String subject,
                                                         String description, TicketPriority priority){
        if(user == null || Helper.isEmptyOrNull(subject)){
            return null;
        }

        SupportTicket.Builder builder = new SupportTicket.Builder()
                .setUser(user)
                .setSubject(subject)
                .setDescription(description);

        if(booking != null){
            builder.setBooking(booking);
        }

        if(priority != null){
            builder.setPriority(priority);
        }

        return builder.build();
    }

    /**
     * Copy an existing ticket (for updates)
     * @param ticket The ticket to copy
     * @return A copy of the ticket or null if ticket is null
     */
    public static SupportTicket copyTicket(SupportTicket ticket) {
        if (ticket == null) {
            return null;
        }
        return new SupportTicket.Builder()
                .copy(ticket)
                .build();
    }
}
