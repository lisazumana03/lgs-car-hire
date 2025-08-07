package za.co.carhire.factory.reservation;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 14 May 2025
 */

public class SupportTicketFactory {

    //Support Submission
    public static SupportTicket submitTicket(int ticketID, User user, String message){
        if(Helper.isWithinBoundary(ticketID)||
           Helper.isEmptyOrNull(message)){
            return null;
        }
        else{
            return new SupportTicket.Builder()
                    .setTicketID(ticketID)
                    .setUser(user)
                    .setMessage(message)
                    .setStatus("Open")
                    .setResponse(0)
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
                    .setStatus("Closed")
                    .build();
        }
    }
}
