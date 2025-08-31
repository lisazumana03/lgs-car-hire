package za.co.carhire.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.LoginRequest;
import za.co.carhire.dto.SignUpRequest;
import za.co.carhire.dto.UserDTO;
import za.co.carhire.factory.authentication.UserFactory;
import za.co.carhire.mapper.UserMapper;
import za.co.carhire.service.authentication.UserService;

import java.util.List;

/* User.java

     User Controller class

     Author: Bonga Velem

     Student Number: 220052379

     */
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService service;

  @Autowired
  private UserMapper mapper;

  @PostMapping("/create")
  public UserDTO createUser(@RequestBody final UserDTO userDTO) {
    User user = mapper.toDomain(userDTO);
    User createdUser = service.save(user);
    return mapper.toDTO(createdUser);
  }

  @PostMapping("/signup")
  public UserDTO signUp(@RequestBody final SignUpRequest signUpRequest) {
    User user = new User.Builder()
        .setIdNumber(signUpRequest.getIdNumber())
        .setName(signUpRequest.getName())
        .setEmail(signUpRequest.getEmail())
        .setDateOfBirth(signUpRequest.getDateOfBirth())
        .setPhoneNumber(signUpRequest.getPhoneNumber())
        .setPassword(signUpRequest.getPassword())
        .setLicenseNumber(signUpRequest.getLicenseNumber())
        .build();

    User createdUser = service.save(user);
    return mapper.toDTO(createdUser);
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginDetails) {
    User user = service.findByEmailAndPassword(
        loginDetails.getEmail(),
        loginDetails.getPassword());
    if (user != null) {
      return ResponseEntity.ok(mapper.toDTO(user));
    }
    return ResponseEntity.status(404).build();
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
    User existingUser = service.read(id);
    if (existingUser == null) {
      return ResponseEntity.notFound().build();
    }

    User user = mapper.toDomain(userDTO);
    user.setUserId(id);
    User updatedUser = service.update(user);
    return ResponseEntity.ok(mapper.toDTO(updatedUser));
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
