package za.co.carhire.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lgs-car-hire")
public class AdminController {
    @RequestMapping("/admin")
    public String admin() {
        return "Welcome to the admin system";
    }
}
