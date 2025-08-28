package za.co.carhire.service.impl.reservation;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.factory.authentication.UserFactory;
import za.co.carhire.factory.reservation.NotificationFactory;
import za.co.carhire.repository.reservation.INotificationRepository;
import za.co.carhire.service.reservation.NotificationService;
import za.co.carhire.service.reservation.impl.NotificationServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceImplTest {
    private static NotificationServiceImpl service;
    private static User user = UserFactory.createUser(
            "920123456789",
            "John Doe",
            "john@example.com",
            "1992-01-01",
            "0711234567",
            "pass123",
            "L123456");

    private static Notification notification = NotificationFactory.createNotification(
            user,
            "Welcome to the system",
            LocalDate.now().toString(),
            BookingStatus.BOOKED);

    @Test
    @Order(1)
    void save() {
        Notification saved = service.save(notification);
        assertNotNull(saved);
        System.out.println("Saved: " + saved);
    }

    @Test
    @Order(2)
    void read() {
        Notification read = service.read(notification.getNotificationID());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void findByUserId() {
        List<Notification> userNotifs = service.findByUserId(1001L);
        assertFalse(userNotifs.isEmpty());
        System.out.println("Found by userId: " + userNotifs);
    }

    @Test
    @Order(4)
    void findByUserIdAndStatus() {
        List<Notification> notifs = service.findByUserIdAndStatus(1001L, "BOOKED");
        assertFalse(notifs.isEmpty());
        System.out.println("Found by userId and status: " + notifs);
    }

    @Test
    @Order(5)
    void findAll() {
        List<Notification> all = service.findAll();
        assertFalse(all.isEmpty());
        System.out.println("All: " + all);
    }

    @Test
    @Order(6)
    void delete() {
        service.delete(notification.getNotificationID());
        assertNull(service.read(notification.getNotificationID()));
        System.out.println("Deleted notification ID: " + notification.getNotificationID());
    }

    @Test
    @Order(7)
    void update() {
        Notification updated = new Notification.Builder()
                .copy(notification)
                .setMessage("Your booking has been updated")
                .setStatus(BookingStatus.valueOf(BookingStatus.CANCELLED.name()))
                .build();

        Notification result = service.update(updated);
        assertNotNull(result);
        assertEquals("Your booking has been updated", result.getMessage());
        System.out.println("Updated Notification: " + result);
    }
}