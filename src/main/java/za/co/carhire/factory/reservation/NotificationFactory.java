package za.co.carhire.factory.reservation;
/* NotifricationFactory.java

     NotificationFactory/factory class

     Author: Bonga Velem

     Student Number: 220052379

     */
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.util.Helper;
import za.co.carhire.domain.reservation.Notification;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotificationFactory {


    public static Notification createNotification(int notificationID, User user, String message, boolean readStatus, LocalDateTime dateSent){

        if (Helper.isNull(user)|| Helper.isNullOrEmpty(message)|| Helper.isNull(dateSent)){
            return null;
        }
        return new Notification.Builder()
                .setNotificationID(notificationID)
                .setUser(user)
                .setMessage(message)
                .setReadStatus(readStatus)
                .setDateSent(dateSent)
                .build();

    }
}
