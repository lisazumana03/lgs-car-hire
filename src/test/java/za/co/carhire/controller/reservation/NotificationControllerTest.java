package za.co.carhire.controller.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.mapper.NotificationMapper;
import za.co.carhire.service.authentication.UserService;
import za.co.carhire.service.reservation.NotificationService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationControllerTest {

        @Autowired
        private NotificationService notificationService;

        @Autowired
        private UserService userService;

        @Autowired
        private NotificationMapper notificationMapper;

        private User testUser;
        private Integer userId;

        @BeforeEach
        void setUp() {
                Long uniqueIdNumber = System.currentTimeMillis() % 1000000000L;
                testUser = new User.Builder()
                                .setName("Test User")
                                .setEmail("test@example.com")
                                .setIdNumber(uniqueIdNumber)
                                .setDateOfBirth(LocalDate.of(1990, 1, 1))
                                .setPhoneNumber("1234567890")
                                .setPassword("password123")
                                .setLicenseNumber("LIC" + uniqueIdNumber)
                                .build();

                User savedUser = userService.save(testUser);
                userId = savedUser.getUserId();
        }

        @Test
        void testCreateNotification() {
                NotificationDTO dto = new NotificationDTO(
                                null,
                                "Test notification message",
                                LocalDate.now(),
                                "BOOKED",
                                userId,
                                "Test User");

                Notification notification = notificationMapper.toDomain(dto);
                Notification saved = notificationService.save(notification);

                assertNotNull(saved);
                assertNotNull(saved.getNotificationID());
                assertEquals("Test notification message", saved.getMessage());
                assertEquals("BOOKED", saved.getStatus().toString());
                assertEquals(userId, saved.getUser().getUserId());
        }

        @Test
        void testReadNotification() {
                Notification notification = new Notification.Builder()
                                .setMessage("Read test message")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.PENDING)
                                .setUserID(testUser)
                                .build();

                Notification saved = notificationService.save(notification);
                Integer notificationId = saved.getNotificationID();

                Notification found = notificationService.read(notificationId);

                assertNotNull(found);
                assertEquals(notificationId, found.getNotificationID());
                assertEquals("Read test message", found.getMessage());
                assertEquals("PENDING", found.getStatus().toString());
        }

        @Test
        void testUpdateNotification() {
                Notification notification = new Notification.Builder()
                                .setMessage("Original message")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.PENDING)
                                .setUserID(testUser)
                                .build();

                Notification saved = notificationService.save(notification);
                Integer notificationId = saved.getNotificationID();

                NotificationDTO updateDTO = new NotificationDTO(
                                notificationId,
                                "Updated message",
                                LocalDate.now(),
                                "BOOKED",
                                userId,
                                "Test User");

                Notification updatedNotification = notificationMapper.toDomain(updateDTO);
                updatedNotification.setNotificationID(notificationId);

                Notification updated = notificationService.save(updatedNotification);

                assertNotNull(updated);
                assertEquals(notificationId, updated.getNotificationID());
                assertEquals("Updated message", updated.getMessage());
                assertEquals("BOOKED", updated.getStatus().toString());
        }

        @Test
        void testDeleteNotification() {
                Notification notification = new Notification.Builder()
                                .setMessage("Delete test message")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.CANCELLED)
                                .setUserID(testUser)
                                .build();

                Notification saved = notificationService.save(notification);
                Integer notificationId = saved.getNotificationID();

                assertNotNull(notificationService.read(notificationId));

                notificationService.delete(notificationId);

                assertNull(notificationService.read(notificationId));
        }

        @Test
        void testGetAllNotifications() {
                Notification notification1 = new Notification.Builder()
                                .setMessage("First notification")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.BOOKED)
                                .setUserID(testUser)
                                .build();

                Notification notification2 = new Notification.Builder()
                                .setMessage("Second notification")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.PENDING)
                                .setUserID(testUser)
                                .build();

                notificationService.save(notification1);
                notificationService.save(notification2);

                List<Notification> allNotifications = notificationService.findAll();

                assertNotNull(allNotifications);
                assertTrue(allNotifications.size() >= 2);
        }

        @Test
        void testNotificationMapper() {
                NotificationDTO dto = new NotificationDTO(
                                null,
                                "Mapper test message",
                                LocalDate.now(),
                                "BOOKED",
                                userId,
                                "Test User");

                Notification domain = notificationMapper.toDomain(dto);
                assertNotNull(domain);
                assertEquals("Mapper test message", domain.getMessage());
                assertEquals("BOOKED", domain.getStatus().toString());
                assertEquals(userId, domain.getUser().getUserId());

                NotificationDTO convertedDTO = notificationMapper.toDTO(domain);
                assertNotNull(convertedDTO);
                assertEquals("Mapper test message", convertedDTO.getMessage());
                assertEquals("BOOKED", convertedDTO.getStatus());
                assertEquals(userId, convertedDTO.getUserId());
                assertEquals("Test User", convertedDTO.getUserName());
        }

        @Test
        void testNotificationNotFound() {
                Notification notFound = notificationService.read(99999);
                assertNull(notFound);
        }

        @Test
        void testCreateNotificationWithEmptyMessage() {
                NotificationDTO dto = new NotificationDTO(
                                null,
                                "",
                                LocalDate.now(),
                                "BOOKED",
                                userId,
                                "Test User");

                Notification notification = notificationMapper.toDomain(dto);
                Notification saved = notificationService.save(notification);

                assertNotNull(saved);
                assertEquals("", saved.getMessage());
        }
}