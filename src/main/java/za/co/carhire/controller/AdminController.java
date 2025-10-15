package za.co.carhire.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 * Author: Imtiyaaz Waggie 219374759
 * Date: 2025-10-15
 */
@RestController
@RequestMapping("/api/lgs-car-hire")
public class AdminController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Welcome to the admin system. You have ADMIN privileges.";
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Admin Dashboard - View system statistics and manage users";
    }
}
