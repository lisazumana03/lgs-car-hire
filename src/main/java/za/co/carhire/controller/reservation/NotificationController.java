package za.co.carhire.controller.reservation;
/* NotificationController.java

     Notification reservation/Controller class

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.mapper.NotificationMapper;
import za.co.carhire.service.reservation.NotificationService;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @Autowired
    private NotificationMapper mapper;

    @PostMapping
    public NotificationDTO create(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = mapper.toDomain(notificationDTO);
        Notification created = service.save(notification);
        return mapper.toDTO(created);
    }

    @GetMapping("/{id}")
    public NotificationDTO read(@PathVariable Integer id) {
        Notification notification = service.read(id);
        return mapper.toDTO(notification);
    }

    @PutMapping("/{id}")
    public NotificationDTO update(@PathVariable Integer id, @RequestBody NotificationDTO notificationDTO) {
        Notification existingNotification = service.read(id);
        if (existingNotification == null) {
            return null;
        }

        Notification notification = mapper.toDomain(notificationDTO);
        notification.setNotificationID(id);
        Notification updated = service.save(notification);
        return mapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public List<NotificationDTO> getAll() {
        List<Notification> notifications = service.findAll();
        return mapper.toDTOList(notifications);
    }
}
