package za.co.carhire.factory.reservation;
import za.co.carhire.domain.reservation.SupportTicket;
import za.co.carhire.util.Helper;
import java.util.Date;

/*
Olwethu Tshingo - 222634383
Date: 14 May 2025
 */

public class SupportTicketFactory {

    //Support Submission
    public static SupportTicket submitTicket(int ticketID, int userID, String message){
        if(Helper.isWithinBoundary(ticketID)||
           Helper.isWithinBoundary(userID)||
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
//                    .setCreatedAt(new Date())
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
