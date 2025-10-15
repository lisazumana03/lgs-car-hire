package za.co.carhire.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.LoginRequest;
import za.co.carhire.dto.SignUpRequest;
import za.co.carhire.dto.UserDTO;
import za.co.carhire.factory.authentication.UserFactory;
import za.co.carhire.mapper.UserMapper;
import za.co.carhire.service.authentication.UserService;

import java.util.List;

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
  private UserService service;

  @Autowired
  private UserMapper mapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/create")
  public UserDTO createUser(@RequestBody final UserDTO userDTO) {
    User user = mapper.toDomain(userDTO);
    User createdUser = service.save(user);
    return mapper.toDTO(createdUser);
  }

  @PostMapping("/signup")
  public UserDTO signUp(@RequestBody final SignUpRequest signUpRequest) {

    String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

    Role userRole = signUpRequest.getRole() != null ? signUpRequest.getRole() : Role.CUSTOMER;

    User user = new User.Builder()
        .setIdNumber(signUpRequest.getIdNumber())
        .setName(signUpRequest.getName())
        .setEmail(signUpRequest.getEmail())
        .setDateOfBirth(signUpRequest.getDateOfBirth())
        .setPhoneNumber(signUpRequest.getPhoneNumber())
        .setPassword(hashedPassword)
        .setLicenseNumber(signUpRequest.getLicenseNumber())
        .setRole(userRole)
        .build();

    User createdUser = service.save(user);
    return mapper.toDTO(createdUser);
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginDetails) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginDetails.getEmail(),
              loginDetails.getPassword()
          )
      );

      User user = service.findByEmail(loginDetails.getEmail());
      if (user != null) {
        return ResponseEntity.ok(mapper.toDTO(user));
      }
      return ResponseEntity.status(404).build();

    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> read(@PathVariable Integer id) {
    User user = service.read(id);
    if (user != null) {
      return ResponseEntity.ok(mapper.toDTO(user));
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
    try {
      System.out.println("INFO: Updating user with ID: " + id);
      System.out.println("INFO: Received DTO: " + userDTO);

      User existingUser = service.read(id);
      if (existingUser == null) {
        System.err.println("ERROR: User not found with ID: " + id);
        return ResponseEntity.notFound().build();
      }

      User user = mapper.toDomain(userDTO);
      user.setUserId(id);

      // Preserve password from existing user
      user.setPassword(existingUser.getPassword());

      User updatedUser = service.update(user);
      System.out.println("SUCCESS: User updated successfully: " + updatedUser.getName());
      return ResponseEntity.ok(mapper.toDTO(updatedUser));
    } catch (Exception e) {
      System.err.println("ERROR: Error updating user: " + e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    if (service.read(id) != null) {
      service.delete(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/all")
  public List<UserDTO> getAll() {
    List<User> users = service.findAll();
    return mapper.toDTOList(users);
  }
}
