package za.co.carhire.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.dto.UserDTO;
import za.co.carhire.mapper.UserMapper;
import za.co.carhire.service.authentication.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
// @CrossOrigin(origins = "http://localhost:3046")
public class UserController {

    @Autowired
    private UserService service;

    // ========== Public Endpoints (No authentication required) ==========

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (user.getRole() == null) {
                user.setRole(UserRole.CUSTOMER);
            }

            User registeredUser = service.register(user);
            UserDTO dto = UserMapper.toDTO(registeredUser);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User authenticatedUser = service.authenticate(email, password);
        if (authenticatedUser != null) {
            UserDTO dto = UserMapper.toDTO(authenticatedUser);
            return new ResponseEntity<>(Map.of(
                    "user", dto,
                    "message", "Login successful",
                    "role", authenticatedUser.getRole().toString()
            ), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Invalid email or password"), HttpStatus.UNAUTHORIZED);
    }

    // ========== Customer Endpoints ==========

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Integer id) {
        User user = service.read(id);
        if (user != null) {
            UserDTO dto = UserMapper.toDTO(user);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody UserDTO userDto) {
        User existingUser = service.read(id);
        if (existingUser != null) {
            // Preserve the role and password
            UserRole currentRole = existingUser.getRole();
            String currentPassword = existingUser.getPassword();

            User updatedUser = UserMapper.updateEntityFromDTO(existingUser, userDto);
            updatedUser.setRole(currentRole);
            updatedUser.setPassword(currentPassword);

            User savedUser = service.update(updatedUser);
            UserDTO responseDto = UserMapper.toDTO(savedUser);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Integer id,
                                            @RequestBody Map<String, String> passwords) {
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");

        boolean success = service.changePassword(id, oldPassword, newPassword);
        if (success) {
            return new ResponseEntity<>(Map.of("message", "Password changed successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Invalid old password"), HttpStatus.BAD_REQUEST);
    }

    // ========== Admin Endpoints ==========

    @PostMapping("/admin/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // This endpoint allows admins to create users with any role
        try {
            User createdUser = service.save(user);
            UserDTO dto = UserMapper.toDTO(createdUser);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = service.findAll();
        List<UserDTO> userDtos = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/admin/customers")
    public ResponseEntity<List<UserDTO>> getAllCustomers() {
        List<User> customers = service.findAllCustomers();
        List<UserDTO> customerDtos = customers.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping("/admin/admins")
    public ResponseEntity<List<UserDTO>> getAllAdmins() {
        List<User> admins = service.findAllAdmins();
        List<UserDTO> adminDtos = admins.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(adminDtos, HttpStatus.OK);
    }

    @PutMapping("/admin/promote/{id}")
    public ResponseEntity<?> promoteToAdmin(@PathVariable Integer id) {
        boolean success = service.promoteToAdmin(id);
        if (success) {
            User user = service.read(id);
            UserDTO dto = UserMapper.toDTO(user);
            return new ResponseEntity<>(Map.of(
                    "message", "User promoted to admin",
                    "user", dto
            ), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Cannot promote user"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/admin/demote/{id}")
    public ResponseEntity<?> demoteToCustomer(@PathVariable Integer id) {
        boolean success = service.demoteToCustomer(id);
        if (success) {
            User user = service.read(id);
            UserDTO dto = UserMapper.toDTO(user);
            return new ResponseEntity<>(Map.of(
                    "message", "User demoted to customer",
                    "user", dto
            ), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Cannot demote user"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        User user = service.read(id);
        if (user != null) {
            service.delete(id);
            return new ResponseEntity<>(Map.of("message", "User deleted successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/by-email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User user = service.findByEmail(email);
        if (user != null) {
            UserDTO dto = UserMapper.toDTO(user);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/can-rent/{id}")
    public ResponseEntity<?> checkCanRent(@PathVariable Integer id) {
        boolean canRent = service.canRentCar(id);
        return new ResponseEntity<>(Map.of(
                "userId", id,
                "canRent", canRent
        ), HttpStatus.OK);
    }
}