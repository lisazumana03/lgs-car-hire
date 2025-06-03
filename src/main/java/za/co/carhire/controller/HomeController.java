package za.co.carhire.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lgs-car-hire")
public class HomeController {
    @RequestMapping("/main")
    public String home() {
        return "Welcome to the Car Hire System";
    }
}
