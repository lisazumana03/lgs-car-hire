package za.co.carhire.factory.reservation;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.util.Helper;

/*
Olwethu Tshingo - 222634383
Date: 14 May 2025
 */

public class SupportTicketFactory {

    //Support Submission
    public static SupportTicket submitTicket(int ticketID, int userID, String message){
        if(Helper.isWithinBoundary(ticketID) ||
           Helper.isWithinBoundary(userID) ||
           Helper.isNullOrEmpty(message)){
            return null;
        }
        else{
            return new SupportTicket.Builder()
                    .setTicketID(ticketID)
                    .setUserID(userID)
                    .setMessage(message)
                    .setStatus("Open")
                    .setResponse(0)
                    .setCreatedAt(new java.util.Date())
                    .build();
        }
    }

    //Ticket Closure
    public static SupportTicket closeTicket(SupportTicket ticket) {
        if (ticket == null) {
            return null;
        }
        else{
            return new SupportTicket.Builder()
                    .copy(ticket)
                    .setStatus("Closed")
                    .build();
        }
    }
}
