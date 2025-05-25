package za.co.carhire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.service.reservation.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return service.save(notification);
    }

    @GetMapping("/{id}")
    public Notification read(@PathVariable Integer id) {
        return service.read(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public List<Notification> getAll() {
        return service.findAll();
    }
}
