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
    public static Notification createNotification(Integer notificationID, User userID, String message, String dateSent, BookingStatus status){
        if(Helper.isEmptyOrNull(message)|| Helper.isValidDate(dateSent)|| Helper.isEmptyOrNull(status)){
            return null;
        }
        return new Notification.Builder()
                .setNotificationID(notificationID)
                .setUserID(userID)
                .setMessage(message)
                .setDateSent(LocalDate.parse(dateSent))
                .setStatus(status)
                .build();


    }


}
