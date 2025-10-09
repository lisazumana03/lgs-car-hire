package za.co.carhire.factory.reservation;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 30 July 2025
 */

public class SupportTicketFactory {

    //Support Submission
    public static SupportTicket submitTicket(int ticketID, User user, String subject,String description){
        if(Helper.isWithinBoundary(ticketID)||
           Helper.isEmptyOrNull(subject)){
            return null;
        }
        else{
            return new SupportTicket.Builder()
                    .setTicketID(ticketID)
                    .setUser(user)
                    .setSubject(subject)
                    .setDescription(description)
                    .build();
        }
    }

    //Ticket Closure

    public static SupportTicket closeTicket(int ticketID) {
        if (Helper.isWithinBoundary(ticketID)) {
            return null;
        }
        else{
            return new SupportTicket.Builder()
                    .setSubject("Closed")
                    .build();
        }
    }
}
