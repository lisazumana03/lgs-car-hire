package za.co.carhire.service.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.dto.CreateNotificationDTO;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.reservation.INotificationRepository;
import za.co.carhire.service.reservation.impl.NotificationServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * NotificationServiceImplTest
 * 
 * Unit tests for NotificationServiceImpl
 * 
 * Author: Bonga Velem
 * Student Number: 220052379
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

  @Mock
  private INotificationRepository notificationRepository;

  @Mock
  private IUserRepository userRepository;

  @InjectMocks
  private NotificationServiceImpl notificationService;

  private User testUser;
  private Notification testNotification;
  private CreateNotificationDTO createDTO;

  @BeforeEach
  void setUp() {
    testUser = new User.Builder()
        .setUserId(1)
        .setIdNumber(9001015800087L)
        .setFirstName("John")
        .setLastName("Doe")
        .setEmail("john.doe@example.com")
        .setDateOfBirth(LocalDate.of(1990, 1, 1))
        .setPhoneNumber("0123456789")
        .setPassword("hashedPassword")
        .setRole(Role.CUSTOMER)
        .build();

    testNotification = new Notification.Builder()
        .setNotificationID(1)
        .setUser(testUser)
        .setMessage("Your booking has been confirmed!")
        .setDateSent(LocalDateTime.now())
        .setReadStatus(false)
        .build();

    createDTO = new CreateNotificationDTO(1, "Your booking has been confirmed!");
  }

  @Test
  void testSendNotification_Success() {
    // Given
    when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
    when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

    // When
    NotificationDTO result = notificationService.sendNotification(createDTO);

    // Then
    assertNotNull(result);
    assertEquals(1, result.notificationID());
    assertEquals(1, result.userId());
    assertEquals("Your booking has been confirmed!", result.message());
    assertFalse(result.readStatus());
    verify(userRepository, times(1)).findById(1);
    verify(notificationRepository, times(1)).save(any(Notification.class));
  }

  @Test
  void testSendNotification_UserNotFound_ThrowsException() {
    // Given
    when(userRepository.findById(999)).thenReturn(Optional.empty());

    CreateNotificationDTO invalidDTO = new CreateNotificationDTO(999, "Test message");

    // When & Then
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> notificationService.sendNotification(invalidDTO));

    assertEquals("User not found with ID: 999", exception.getMessage());
    verify(userRepository, times(1)).findById(999);
    verify(notificationRepository, never()).save(any());
  }

  @Test
  void testGetUserNotifications_Success() {
    // Given
    Notification notification2 = new Notification.Builder()
        .setNotificationID(2)
        .setUser(testUser)
        .setMessage("Payment received")
        .setDateSent(LocalDateTime.now())
        .setReadStatus(true)
        .build();

    List<Notification> notifications = Arrays.asList(testNotification, notification2);

    when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
    when(notificationRepository.findByUser(testUser)).thenReturn(notifications);

    // When
    List<NotificationDTO> result = notificationService.getUserNotifications(1);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(1, result.get(0).notificationID());
    assertEquals(2, result.get(1).notificationID());
    assertFalse(result.get(0).readStatus());
    assertTrue(result.get(1).readStatus());
    verify(userRepository, times(1)).findById(1);
    verify(notificationRepository, times(1)).findByUser(testUser);
  }

  @Test
  void testGetUserNotifications_UserNotFound_ThrowsException() {
    // Given
    when(userRepository.findById(999)).thenReturn(Optional.empty());

    // When & Then
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> notificationService.getUserNotifications(999));

    assertEquals("User not found with ID: 999", exception.getMessage());
    verify(userRepository, times(1)).findById(999);
    verify(notificationRepository, never()).findByUser(any());
  }

  @Test
  void testGetUserNotifications_EmptyList() {
    // Given
    when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
    when(notificationRepository.findByUser(testUser)).thenReturn(Arrays.asList());

    // When
    List<NotificationDTO> result = notificationService.getUserNotifications(1);

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(userRepository, times(1)).findById(1);
    verify(notificationRepository, times(1)).findByUser(testUser);
  }

  @Test
  void testMarkAsRead_Success() {
    // Given
    Notification updatedNotification = new Notification.Builder()
        .copy(testNotification)
        .setReadStatus(true)
        .build();

    when(notificationRepository.findById(1)).thenReturn(Optional.of(testNotification));
    when(notificationRepository.save(any(Notification.class))).thenReturn(updatedNotification);

    // When
    NotificationDTO result = notificationService.markAsRead(1);

    // Then
    assertNotNull(result);
    assertEquals(1, result.notificationID());
    assertTrue(result.readStatus());
    verify(notificationRepository, times(1)).findById(1);
    verify(notificationRepository, times(1)).save(any(Notification.class));
  }

  @Test
  void testMarkAsRead_NotificationNotFound_ThrowsException() {
    // Given
    when(notificationRepository.findById(999)).thenReturn(Optional.empty());

    // When & Then
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> notificationService.markAsRead(999));

    assertEquals("Notification not found with ID: 999", exception.getMessage());
    verify(notificationRepository, times(1)).findById(999);
    verify(notificationRepository, never()).save(any());
  }

  @Test
  void testMarkAsRead_AlreadyRead_Success() {
    // Given
    Notification alreadyReadNotification = new Notification.Builder()
        .copy(testNotification)
        .setReadStatus(true)
        .build();

    when(notificationRepository.findById(1)).thenReturn(Optional.of(alreadyReadNotification));
    when(notificationRepository.save(any(Notification.class))).thenReturn(alreadyReadNotification);

    // When
    NotificationDTO result = notificationService.markAsRead(1);

    // Then
    assertNotNull(result);
    assertEquals(1, result.notificationID());
    assertTrue(result.readStatus());
    verify(notificationRepository, times(1)).findById(1);
    verify(notificationRepository, times(1)).save(any(Notification.class));
  }
}
