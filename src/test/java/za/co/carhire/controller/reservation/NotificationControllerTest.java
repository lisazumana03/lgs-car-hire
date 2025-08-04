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

/**
 * Simple Integration tests for NotificationController using DTOs
 *
 *
 * Author: Bonga Velem (220052379)
 * Date: 18 May 2025
 */
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
                // Create a test user for all tests
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

                // Save user and get ID
                User savedUser = userService.save(testUser);
                userId = savedUser.getUserId();
        }

        @Test
        void testCreateNotification() {
                // Create DTO
                NotificationDTO dto = new NotificationDTO(
                                null,
                                "Test notification message",
                                LocalDate.now(),
                                "BOOKED",
                                userId,
                                "Test User");

                // Convert to domain object
                Notification notification = notificationMapper.toDomain(dto);

                // Save to database
                Notification saved = notificationService.save(notification);

                // Assertions
                assertNotNull(saved);
                assertNotNull(saved.getNotificationID());
                assertEquals("Test notification message", saved.getMessage());
                assertEquals("BOOKED", saved.getStatus().toString());
                assertEquals(userId, saved.getUser().getUserId());
        }

        @Test
        void testReadNotification() {
                // First create a notification
                Notification notification = new Notification.Builder()
                                .setMessage("Read test message")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.PENDING)
                                .setUserID(testUser)
                                .build();

                Notification saved = notificationService.save(notification);
                Integer notificationId = saved.getNotificationID();

                // Read the notification
                Notification found = notificationService.read(notificationId);

                // Assertions
                assertNotNull(found);
                assertEquals(notificationId, found.getNotificationID());
                assertEquals("Read test message", found.getMessage());
                assertEquals("PENDING", found.getStatus().toString());
        }

        @Test
        void testUpdateNotification() {
                // First create a notification
                Notification notification = new Notification.Builder()
                                .setMessage("Original message")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.PENDING)
                                .setUserID(testUser)
                                .build();

                Notification saved = notificationService.save(notification);
                Integer notificationId = saved.getNotificationID();

                // Create updated DTO
                NotificationDTO updateDTO = new NotificationDTO(
                                notificationId,
                                "Updated message",
                                LocalDate.now(),
                                "BOOKED",
                                userId,
                                "Test User");

                // Convert to domain and update
                Notification updatedNotification = notificationMapper.toDomain(updateDTO);
                updatedNotification.setNotificationID(notificationId);

                Notification updated = notificationService.save(updatedNotification);

                // Assertions
                assertNotNull(updated);
                assertEquals(notificationId, updated.getNotificationID());
                assertEquals("Updated message", updated.getMessage());
                assertEquals("BOOKED", updated.getStatus().toString());
        }

        @Test
        void testDeleteNotification() {
                // First create a notification
                Notification notification = new Notification.Builder()
                                .setMessage("Delete test message")
                                .setDateSent(LocalDate.now())
                                .setStatus(za.co.carhire.domain.reservation.BookingStatus.CANCELLED)
                                .setUserID(testUser)
                                .build();

                Notification saved = notificationService.save(notification);
                Integer notificationId = saved.getNotificationID();

                // Verify it exists
                assertNotNull(notificationService.read(notificationId));

                // Delete the notification
                notificationService.delete(notificationId);

                // Verify it's deleted
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

                // Get all notifications
                List<Notification> allNotifications = notificationService.findAll();

                // Assertions
                assertNotNull(allNotifications);
                assertTrue(allNotifications.size() >= 2);
        }

        @Test
        void testNotificationMapper() {
                // Test DTO to Domain conversion
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

                // Test Domain to DTO conversion
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
                                "", // Empty message
                                LocalDate.now(),
                                "BOOKED",
                                userId,
                                "Test User");

                // Convert to domain object
                Notification notification = notificationMapper.toDomain(dto);

                // Save to database (should work even with empty message)
                Notification saved = notificationService.save(notification);

                // Assertions
                assertNotNull(saved);
                assertEquals("", saved.getMessage());
        }
}