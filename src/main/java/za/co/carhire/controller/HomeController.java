package za.co.carhire.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:3046/main")
@RequestMapping("/api/lgs-car-hire")
public class HomeController {
    @RequestMapping("/main")
    public String home() {
        return "Welcome to the Car Hire System";
    }
}
