package za.co.carhire.factory.reservation;

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.util.Helper;
import za.co.carhire.domain.reservation.Notification;
import java.time.LocalDate;

public class NotificationFactory {

    public static Notification createNotification(User user, String message, String dateSent, BookingStatus status){
        if(user == null || Helper.isEmptyOrNull(message) || !Helper.isValidDate(dateSent) || status == null){
            return null;
        }
        return new Notification.Builder()
                .setUserID(user)
                .setMessage(message)
                .setDateSent(LocalDate.parse(dateSent))
                .setStatus(status)
                .build();
    }
}
