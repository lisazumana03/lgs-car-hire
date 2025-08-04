package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.mapper.NotificationMapper;
import za.co.carhire.service.reservation.NotificationService;

import java.util.List;

/**
 * This is the controller that was giving me problems.
 *
 * REST Controller for Notification operations using DTOs
 * 
 * This controller demonstrates the DTO (Data Transfer Object) pattern:
 * - Separates API layer from domain layer
 * - Prevents circular reference issues in JSON serialization
 * - Provides type safety and clear API contracts
 * 
 * Author: Bonga Velem (220052379)
 * Date: 18 May 2025
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    // Inject the mapper to convert between DTOs and domain objects
    @Autowired
    private NotificationMapper mapper;

    @PostMapping
    public NotificationDTO create(@RequestBody NotificationDTO notificationDTO) {
        // Step 1: Convert DTO to domain object using mapper
        Notification notification = mapper.toDomain(notificationDTO);

        // Step 2: Save to database using service layer
        Notification created = service.save(notification);

        // Step 3: Convert back to DTO for response
        return mapper.toDTO(created);
    }

    // READ - Get a single notification by ID
    // Flow: URL Parameter → Database Query → Domain Object → DTO → JSON Response
    @GetMapping("/{id}")
    public NotificationDTO read(@PathVariable Integer id) {
        // Step 1: Fetch from database using service layer
        Notification notification = service.read(id);

        // Step 2: Convert domain object to DTO for response
        return mapper.toDTO(notification);
    }

    /**
     * UPDATE - Update an existing notification
     * 
     * Flow: URL Parameter + JSON Request → NotificationDTO → Domain Object →
     * Database → Response DTO
     * 
     * @param id              The notification ID to update
     * @param notificationDTO The updated notification data
     * @return NotificationDTO with the updated notification details, or null if not
     *         found
     */
    @PutMapping("/{id}")
    public NotificationDTO update(@PathVariable Integer id, @RequestBody NotificationDTO notificationDTO) {
        // Step 1: Check if notification exists
        Notification existingNotification = service.read(id);
        if (existingNotification == null) {
            return null; // Return null if notification not found
        }

        // Step 2: Convert DTO to domain object using mapper
        Notification notification = mapper.toDomain(notificationDTO);

        // Step 3: Set the ID to ensure we update the correct notification
        notification.setNotificationID(id);

        // Step 4: Save updated notification to database
        Notification updated = service.save(notification);

        // Step 5: Convert back to DTO for response
        return mapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public List<NotificationDTO> getAll() {
        // Step 1: Fetch all notifications from database
        List<Notification> notifications = service.findAll();

        // Step 2: Convert each domain object to DTO using mapper
        return mapper.toDTOList(notifications);
    }
}
