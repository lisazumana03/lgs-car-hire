package za.co.carhire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.dto.UserDTO;
import za.co.carhire.mapper.UserMapper;
import za.co.carhire.service.authentication.UserService;

import java.util.List;

/**
 * Admin Controller
 * Handles admin-specific operations
 * All endpoints require ADMIN role
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {
        return ResponseEntity.ok("Welcome to the Admin Dashboard");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(userMapper.toDTOList(users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        User user = userService.read(id);
        if (user != null) {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserDTO> updateUserRole(
            @PathVariable Integer id,
            @RequestParam UserRole role
    ) {
        User user = userService.read(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setRole(role);
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        User user = userService.read(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStats> getStats() {
        List<User> allUsers = userService.findAll();
        long totalUsers = allUsers.size();
        long adminCount = allUsers.stream().filter(u -> u.getRole() == UserRole.ADMIN).count();
        long userCount = allUsers.stream().filter(u -> u.getRole() == UserRole.USER).count();

        AdminStats stats = new AdminStats(totalUsers, adminCount, userCount);
        return ResponseEntity.ok(stats);
    }

    // Inner class for stats response
    public static class AdminStats {
        private long totalUsers;
        private long adminCount;
        private long userCount;

        public AdminStats(long totalUsers, long adminCount, long userCount) {
            this.totalUsers = totalUsers;
            this.adminCount = adminCount;
            this.userCount = userCount;
        }

        public long getTotalUsers() {
            return totalUsers;
        }

        public long getAdminCount() {
            return adminCount;
        }

        public long getUserCount() {
            return userCount;
        }
    }
}
