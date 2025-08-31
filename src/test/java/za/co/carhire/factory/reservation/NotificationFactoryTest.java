package za.co.carhire.factory.reservation;
/* NotificationFactoryTest.java

     NotificationFactoryest/authentication/factory class Test

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Notification;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void createNotification() {
        User user = new User.Builder()
                .setUserId(12132)
                .build();

        Notification notification = NotificationFactory.createNotification(
                user,
                "Welcome to Our App!",
                "2025-05-18",
                BookingStatus.BOOKED
        );

        assertNotNull(notification);
        System.out.println(notification);
    }
}