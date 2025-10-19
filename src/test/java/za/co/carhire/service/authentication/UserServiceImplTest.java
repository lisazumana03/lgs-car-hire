package za.co.carhire.service.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.authenticationDTO.LoginRequestDTO;
import za.co.carhire.dto.authenticationDTO.SignUpRequestDTO;
import za.co.carhire.dto.authenticationDTO.UserDTO;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.authentication.Impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * UserServiceImplTest
 * 
 * Unit tests for UserServiceImpl
 * 
 * Author: Bonga Velem
 * Student Number: 220052379
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private IUserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  private User testUser;
  private UserDTO testUserDTO;
  private SignUpRequestDTO signUpRequestDTO;

  @BeforeEach
  void setUp() {
    testUser = new User.Builder()
        .setUserId(1)
        .setIdNumber(9001015800087L)
        .setFirstName("Bonga")
        .setLastName("Velem")
        .setEmail("bongavelem@example.com")
        .setDateOfBirth(LocalDate.of(1990, 1, 1))
        .setPhoneNumber("0123456789")
        .setPassword("hashedPassword")
        .setRole(Role.CUSTOMER)
        .build();

    testUserDTO = new UserDTO(
        1,
        9001015800087L,
        "J",
        "Doe",
        "john.doe@example.com",
        LocalDate.of(1990, 1, 1),
        "0123456789",
        Role.CUSTOMER);

    signUpRequestDTO = new SignUpRequestDTO(
        null,
        9001015800087L,
        "Bonga",
        "Velem",
        "bongavelem@example.com",
        LocalDate.of(1990, 1, 1),
        "0123456789",
        "Password@123",
        Role.CUSTOMER);
  }

  @Test
  void testListUsers_Success() {
    // Given
    User user2 = new User.Builder()
        .setUserId(2)
        .setIdNumber(9505055800086L)
        .setFirstName("Devin")
        .setLastName("Booker")
        .setEmail("devin.booker@example.com")
        .setDateOfBirth(LocalDate.of(1996, 10, 30))
        .setPhoneNumber("0987654321")
        .setPassword("hashedPassword")
        .setRole(Role.CUSTOMER)
        .build();

    when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));

    // When
    List<UserDTO> result = userService.listUsers();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("bongavelem@example.com", result.get(0).email());
    assertEquals("devin.booker@example.com", result.get(1).email());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testListUsers_EmptyList() {
    // Given
    when(userRepository.findAll()).thenReturn(Arrays.asList());

    // When
    List<UserDTO> result = userService.listUsers();

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  @Deprecated
  void testRegisterUser_Success() {
    // Given
    when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    // When
    UserDTO result = userService.registerUser(signUpRequestDTO);

    // Then
    assertNotNull(result);
    assertEquals("bongavelem@example.com", result.email());
    assertEquals("Bonga", result.firstName());
    assertEquals(Role.CUSTOMER, result.role());
    verify(passwordEncoder, times(1)).encode("Password@123");
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  @Deprecated
  void testLoginUser_Success() {
    // Given
    LoginRequestDTO loginDTO = new LoginRequestDTO("bongavelem@example.com", "Password@123");
    when(userRepository.findByEmail("bongavelem@example.com")).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches("Password@123", "hashedPassword")).thenReturn(true);

    // When
    Optional<UserDTO> result = userService.loginUser(loginDTO);

    // Then
    assertTrue(result.isPresent());
    assertEquals("bongavelem@example.com", result.get().email());
    assertEquals("Bonga", result.get().firstName());
    verify(userRepository, times(1)).findByEmail("bongavelem@example.com");
    verify(passwordEncoder, times(1)).matches("Password@123", "hashedPassword");
  }

  @Test
  @Deprecated
  void testLoginUser_WrongPassword_ReturnsEmpty() {
    // Given
    LoginRequestDTO loginDTO = new LoginRequestDTO("bongavelem@example.com", "WrongPassword");
    when(userRepository.findByEmail("bongavelem@example.com")).thenReturn(Optional.of(testUser));
    when(passwordEncoder.matches("WrongPassword", "hashedPassword")).thenReturn(false);

    // When
    Optional<UserDTO> result = userService.loginUser(loginDTO);

    // Then
    assertFalse(result.isPresent());
    verify(userRepository, times(1)).findByEmail("bongavelem@example.com");
    verify(passwordEncoder, times(1)).matches("WrongPassword", "hashedPassword");
  }

  @Test
  @Deprecated
  void testLoginUser_UserNotFound_ReturnsEmpty() {
    // Given
    LoginRequestDTO loginDTO = new LoginRequestDTO("nonexistent@example.com", "Password@123");
    when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

    // When
    Optional<UserDTO> result = userService.loginUser(loginDTO);

    // Then
    assertFalse(result.isPresent());
    verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    verify(passwordEncoder, never()).matches(anyString(), anyString());
  }

  @Test
  void testGetUserProfile_Success() {
    // Given
    when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

    // When
    Optional<UserDTO> result = userService.getUserProfile(1);

    // Then
    assertTrue(result.isPresent());
    assertEquals("bongavelem@example.com", result.get().email());
    assertEquals(1, result.get().userId());
    verify(userRepository, times(1)).findById(1);
  }

  @Test
  void testGetUserProfile_NotFound_ReturnsEmpty() {
    // Given
    when(userRepository.findById(999)).thenReturn(Optional.empty());

    // When
    Optional<UserDTO> result = userService.getUserProfile(999);

    // Then
    assertFalse(result.isPresent());
    verify(userRepository, times(1)).findById(999);
  }

  @Test
  void testUpdateUserProfile_Success() {
    // Given
    UserDTO updateDTO = new UserDTO(
        1,
        9001015800087L,
        "Bonga",
        "Velem Updated",
        "bonga.updated@example.com",
        LocalDate.of(1990, 1, 1),
        "0123456789",
        Role.CUSTOMER);

    User updatedUser = new User.Builder()
        .copy(testUser)
        .setLastName("Velem Updated")
        .setEmail("bonga.updated@example.com")
        .build();

    when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
    when(userRepository.save(any(User.class))).thenReturn(updatedUser);

    // When
    UserDTO result = userService.updateUserProfile(1, updateDTO);

    // Then
    assertNotNull(result);
    assertEquals("Velem Updated", result.lastName());
    assertEquals("bonga.updated@example.com", result.email());
    verify(userRepository, times(1)).findById(1);
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testUpdateUserProfile_UserNotFound_ThrowsException() {
    // Given
    UserDTO updateDTO = new UserDTO(
        999, 9001015800087L, "Devin", "Booker", "devin.booker@example.com",
        LocalDate.of(1996, 10, 30), "0987654321", Role.CUSTOMER);
    when(userRepository.findById(999)).thenReturn(Optional.empty());

    // When & Then
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> userService.updateUserProfile(999, updateDTO));

    assertEquals("User not found: 999", exception.getMessage());
    verify(userRepository, times(1)).findById(999);
    verify(userRepository, never()).save(any());
  }

  @Test
  void testDeleteUser_Success() {
    // Given
    doNothing().when(userRepository).deleteById(1);

    // When
    userService.deleteUser(1);

    // Then
    verify(userRepository, times(1)).deleteById(1);
  }

  @Test
  void testGetUserEntity_Success() {
    // Given
    when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

    // When
    User result = userService.getUserEntity(1);

    // Then
    assertNotNull(result);
    assertEquals(1, result.getUserId());
    assertEquals("bongavelem@example.com", result.getEmail());
    verify(userRepository, times(1)).findById(1);
  }

  @Test
  void testGetUserEntity_NotFound_ReturnsNull() {
    // Given
    when(userRepository.findById(999)).thenReturn(Optional.empty());

    // When
    User result = userService.getUserEntity(999);

    // Then
    assertNull(result);
    verify(userRepository, times(1)).findById(999);
  }
}
