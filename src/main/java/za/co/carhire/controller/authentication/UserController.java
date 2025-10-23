package za.co.carhire.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.dto.authenticationDTO.UpdateUserDTO;
import za.co.carhire.dto.authenticationDTO.UserDTO;
import za.co.carhire.service.authentication.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
public class UserController {

  @Autowired
  private UserService userService;

  // List all users - ADMIN only
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserDTO> listUsers() {
    return userService.listUsers();
  }

  // Get user profile - authenticated users can access their own profile
  @GetMapping("/{userId}")
  public Optional<UserDTO> getProfile(@PathVariable Integer userId) {
    return userService.getUserProfile(userId);
  }

  // Update user profile
  @PutMapping("/{userId}")
  public UserDTO updateProfile(@PathVariable Integer userId, @RequestBody UpdateUserDTO updateDTO) {
    return userService.updateUserProfile(userId, updateDTO);
  }

  // Delete user - ADMIN only
  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUser(@PathVariable Integer userId) {
    userService.deleteUser(userId);
  }
}