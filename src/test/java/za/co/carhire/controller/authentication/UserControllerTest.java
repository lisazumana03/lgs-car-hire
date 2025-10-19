package za.co.carhire.controller.authentication;

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
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.dto.authenticationDTO.UserDTO;
import za.co.carhire.service.authentication.Impl.JwtService;
import za.co.carhire.service.authentication.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserControllerTest
 * 
 * Tests for UserController endpoints
 * 
 * Author: Bonga Velem
 * Student Number: 220052379
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private AuthenticationService authenticationService;

  private UserDTO userDTO;

  @BeforeEach
  void setUp() {
    userDTO = new UserDTO(
        1,
        9001015800087L,
        "Randal",
        "Savage",
        "randall@example.com",
        LocalDate.of(1990, 1, 1),
        "0123456789",
        Role.CUSTOMER);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testListUsers_AsAdmin_Success() throws Exception {
    // Given
    List<UserDTO> users = Arrays.asList(
        userDTO,
        new UserDTO(2, 9505055800086L, "Mary", "Jane", "jane@example.com",
            LocalDate.of(1995, 5, 5), "0987654321", Role.CUSTOMER));
    when(userService.listUsers()).thenReturn(users);

    // When & Then
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].userId").value(1))
        .andExpect(jsonPath("$[0].email").value("randle@example.com"))
        .andExpect(jsonPath("$[1].userId").value(2));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testListUsers_AsCustomer_Success() throws Exception {
    // Given - Customers can also list users
    List<UserDTO> users = Arrays.asList(userDTO);
    when(userService.listUsers()).thenReturn(users);

    // When & Then
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testListUsers_Unauthenticated_Unauthorized() throws Exception {
    // When & Then
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetProfile_Success() throws Exception {
    // Given
    when(userService.getUserProfile(1)).thenReturn(Optional.of(userDTO));

    // When & Then
    mockMvc.perform(get("/api/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.email").value("randall@example.com"))
        .andExpect(jsonPath("$.firstName").value("Randall"))
        .andExpect(jsonPath("$.role").value("CUSTOMER"));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testGetProfile_UserNotFound_ReturnsEmpty() throws Exception {
    // Given
    when(userService.getUserProfile(999)).thenReturn(Optional.empty());

    // When & Then
    mockMvc.perform(get("/api/users/999"))
        .andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  void testGetProfile_Unauthenticated_Unauthorized() throws Exception {
    // When & Then
    mockMvc.perform(get("/api/users/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testUpdateProfile_Success() throws Exception {
    // Given
    UserDTO updateDTO = new UserDTO(
        1,
        9001015800087L,
        "Randall",
        "Updated",
        "randallupdated@example.com",
        LocalDate.of(1990, 1, 1),
        "0123456789",
        Role.CUSTOMER);
    when(userService.updateUserProfile(eq(1), any(UserDTO.class))).thenReturn(updateDTO);

    // When & Then
    mockMvc.perform(put("/api/users/1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.lastName").value("Updated"))
        .andExpect(jsonPath("$.email").value("randallupdated@example.com"));
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testUpdateProfile_UserNotFound_ThrowsException() throws Exception {
    // Given
    UserDTO updateDTO = new UserDTO(
        999, 9001015800087L, "Randall", "Savage", "randallupdated@example.com",
        LocalDate.of(1990, 1, 1), "0123456789", Role.CUSTOMER);
    when(userService.updateUserProfile(eq(999), any(UserDTO.class)))
        .thenThrow(new IllegalArgumentException("User not found: 999"));

    // When & Then
    mockMvc.perform(put("/api/users/999")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void testUpdateProfile_Unauthenticated_Unauthorized() throws Exception {
    // Given
    UserDTO updateDTO = new UserDTO(
        1, 9001015800087L, "Randall", "Savage", "randallsavage@example.com",
        LocalDate.of(1990, 1, 1), "0123456789", Role.CUSTOMER);

    // When & Then
    mockMvc.perform(put("/api/users/1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void testDeleteUser_AsAdmin_Success() throws Exception {
    // Given
    doNothing().when(userService).deleteUser(1);

    // When & Then
    mockMvc.perform(delete("/api/users/1")
        .with(csrf()))
        .andExpect(status().isNoContent());

    verify(userService, times(1)).deleteUser(1);
  }

  @Test
  @WithMockUser(roles = "CUSTOMER")
  void testDeleteUser_AsCustomer_Success() throws Exception {
    // Given - Customers can also delete (might want to restrict this in production)
    doNothing().when(userService).deleteUser(1);

    // When & Then
    mockMvc.perform(delete("/api/users/1")
        .with(csrf()))
        .andExpect(status().isNoContent());

    verify(userService, times(1)).deleteUser(1);
  }

  @Test
  void testDeleteUser_Unauthenticated_Unauthorized() throws Exception {
    // When & Then
    mockMvc.perform(delete("/api/users/1")
        .with(csrf()))
        .andExpect(status().isUnauthorized());

    verify(userService, never()).deleteUser(anyInt());
  }
}
