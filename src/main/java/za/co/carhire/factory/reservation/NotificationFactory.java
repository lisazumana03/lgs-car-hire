package za.co.carhire.factory.reservation;
/* User.java

     User POJO class

     Author: Bonga Velem (220052379)

     Date: 18 May 2025 */

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.util.Helper;
import za.co.carhire.domain.reservation.Notification;

import java.time.LocalDate;

public class NotificationFactory {
    /*validationHelper.generateId(notificationID)|| validationHelper.generateId(userID) ||*/

    public static Notification createNotification(int notificationId, User user, String message, String dateSent, BookingStatus status){
        int id = Helper.generateId(notificationId);
        if(  Helper.isEmptyOrNull(message)|| Helper.isValidDate(dateSent)|| status == null){
            return null;
        }
        return new Notification.Builder()
                .setNotificationID(id)
                .setUserID(user.getUserId())
                .setMessage(message)
                .setDateSent(LocalDate.parse(dateSent))
                .setStatus(status)
                .build();


    }


}
