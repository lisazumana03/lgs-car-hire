package za.co.carhire.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.dto.authenticationDTO.UserDTO;
import za.co.carhire.service.authentication.UserService;

import java.util.List;
import java.util.Optional;

/* UserController.java

     User Controller class

     Author: Bonga Velem

     Student Number: 220052379

     */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
public class UserController {

  @Autowired
  private UserService userService;

  // List all users
  @GetMapping
  public List<UserDTO> listUsers() {
    return userService.listUsers();
  }

  // Register a new user - NOT NEEDED (Use AuthenticationController instead for
  // JWT authentication)
  // @PostMapping("/register")
  // @ResponseStatus(HttpStatus.CREATED)
  // public UserDTO registerUser(@RequestBody SignUpRequestDTO registerDTO) {
  // return userService.registerUser(registerDTO);
  // }

  // Login - NOT NEEDED (Use AuthenticationController instead for JWT
  // authentication)
  // @PostMapping("/login")
  // public Optional<UserDTO> loginUser(@RequestBody LoginRequestDTO loginDTO) {
  // return userService.loginUser(loginDTO);
  // }

  // Get user profile
  @GetMapping("/{userId}")
  public Optional<UserDTO> getProfile(@PathVariable Integer userId) {
    return userService.getUserProfile(userId);
  }

  // Update user profile
  @PutMapping("/{userId}")
  public UserDTO updateProfile(@PathVariable Integer userId, @RequestBody UserDTO updateDTO) {
    return userService.updateUserProfile(userId, updateDTO);
  }

  // Delete user
  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Integer userId) {
    userService.deleteUser(userId);
  }
}
