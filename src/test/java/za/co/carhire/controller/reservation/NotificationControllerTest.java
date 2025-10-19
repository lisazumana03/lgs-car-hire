package za.co.carhire.controller.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import za.co.carhire.config.AuthenticationService;
import za.co.carhire.config.JwtAuthenticationFilter;
import za.co.carhire.dto.CreateNotificationDTO;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.service.authentication.Impl.JwtService;
import za.co.carhire.service.reservation.impl.NotificationServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * NotificationControllerTest
 * 
 * Tests for NotificationController with authorization checks
 * 
 * Author: Bonga Velem
 * Student Number: 220052379
 */
@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private NotificationServiceImpl notificationService;

  @MockBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private AuthenticationService authenticationService;

  private NotificationDTO notificationDTO;
  private CreateNotificationDTO createNotificationDTO;

  @BeforeEach
  void setUp() {
    notificationDTO = new NotificationDTO(
        1,
        1,
        "Your booking has been confirmed!",
        false,
        LocalDateTime.now());

    createNotificationDTO = new CreateNotificationDTO(1, "Your booking has been confirmed!");
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testSendNotification_AsAdmin_Success() throws Exception {
    // Given
    when(notificationService.sendNotification(any(CreateNotificationDTO.class)))
        .thenReturn(notificationDTO);

    // When & Then
    mockMvc.perform(post("/api/notifications")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createNotificationDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.notificationID").value(1))
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.message").value("Your booking has been confirmed!"))
        .andExpect(jsonPath("$.readStatus").value(false));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testSendNotification_AsCustomer_Forbidden() throws Exception {
    // When & Then - Customer should not be able to send notifications
    mockMvc.perform(post("/api/notifications")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createNotificationDTO)))
        .andExpect(status().isForbidden());
  }

  @Test
  void testSendNotification_Unauthenticated_Unauthorized() throws Exception {
    // When & Then - Unauthenticated users cannot send notifications
    mockMvc.perform(post("/api/notifications")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createNotificationDTO)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testSendNotification_ServiceThrowsException_InternalServerError() throws Exception {
    // Given
    when(notificationService.sendNotification(any(CreateNotificationDTO.class)))
        .thenThrow(new RuntimeException("Database error"));

    // When & Then
    mockMvc.perform(post("/api/notifications")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createNotificationDTO)))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.error").value("Internal Server Error"));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetUserNotifications_Success() throws Exception {
    // Given
    List<NotificationDTO> notifications = Arrays.asList(
        notificationDTO,
        new NotificationDTO(2, 1, "Payment received", true, LocalDateTime.now()));
    when(notificationService.getUserNotifications(anyInt())).thenReturn(notifications);

    // When & Then
    mockMvc.perform(get("/api/notifications/user/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].notificationID").value(1))
        .andExpect(jsonPath("$[1].notificationID").value(2));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetUserNotifications_InvalidUserId_BadRequest() throws Exception {
    // When & Then
    mockMvc.perform(get("/api/notifications/user/invalid"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.message").exists());
  }

  @Test
  void testGetUserNotifications_Unauthenticated_Unauthorized() throws Exception {
    // When & Then
    mockMvc.perform(get("/api/notifications/user/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testMarkAsRead_Success() throws Exception {
    // Given
    NotificationDTO readNotification = new NotificationDTO(
        1, 1, "Test message", true, LocalDateTime.now());
    when(notificationService.markAsRead(1)).thenReturn(readNotification);

    // When & Then
    mockMvc.perform(put("/api/notifications/1/read")
        .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.notificationID").value(1))
        .andExpect(jsonPath("$.readStatus").value(true));
  }

  @Test
  void testMarkAsRead_Unauthenticated_Unauthorized() throws Exception {
    // When & Then
    mockMvc.perform(put("/api/notifications/1/read")
        .with(csrf()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testMarkAsRead_NotificationNotFound_ThrowsException() throws Exception {
    // Given
    when(notificationService.markAsRead(anyInt()))
        .thenThrow(new IllegalArgumentException("Notification not found"));

    // When & Then
    mockMvc.perform(put("/api/notifications/999/read")
        .with(csrf()))
        .andExpect(status().isInternalServerError());
  }
}
