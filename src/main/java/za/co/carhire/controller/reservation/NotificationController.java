package za.co.carhire.controller.reservation;

/* NotificationController.java

     Notification reservation/Controller class

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.dto.CreateNotificationDTO;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.service.reservation.impl.NotificationServiceImpl;

import java.util.Map;

@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<?> sendNotification(@RequestBody CreateNotificationDTO createDTO) {
        try {
            System.out.println("Received notification request for userId: " + createDTO.userId() + ", message: "
                    + createDTO.message());
            NotificationDTO savedNotification = notificationService.sendNotification(createDTO);
            return ResponseEntity.ok(savedNotification);
        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Internal Server Error",
                    "message", e.getMessage(),
                    "cause", e.getClass().getName(),
                    "timestamp", java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserNotifications(@PathVariable String userId) {
        try {
            int userIdInt = Integer.parseInt(userId);
            return ResponseEntity.ok(notificationService.getUserNotifications(userIdInt));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Bad Request",
                    "message", "Invalid user ID: " + userId + ". User ID must be a number.",
                    "timestamp", java.time.LocalDateTime.now().toString()));
        }
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable int notificationId) {
        NotificationDTO updated = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(updated);
    }

}
