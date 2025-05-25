package za.co.carhire.factory.reservation;
/* User.java

     User POJO class

     Author: Bonga Velem (220052379)

     Date: 18 May 2025 */

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Notification;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class NotificationFactoryTest {


    @Test
    void createNotification() {
        // Minimal User object (only userId is used)
        User user = new User.Builder()
                .setUserId(12132)
                .build();

        // Create Notification using the factory
        Notification notification = NotificationFactory.createNotification(
                12121,                        // notificationID
                user,                         // User object
                "Welcome to Our App!",        // message
                "2025-05-18",                 // dateSent
                BookingStatus.BOOKED          // status
        );

        assertNotNull(notification);
        System.out.println(notification);
    }
}