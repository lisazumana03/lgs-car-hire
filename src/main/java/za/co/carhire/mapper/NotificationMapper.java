package za.co.carhire.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.service.authentication.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between Notification domain objects and DTOs
 * 
 * This mapper handles the conversion between:
 * - Domain objects (used for database operations)
 * - DTOs (used for API communication)
 *
 * Author: Bonga Velem (220052379)
 * Date: 18 May 2025
 */
@Component
public class NotificationMapper {

    @Autowired
    private UserService userService;


    public NotificationDTO toDTO(Notification notification) {
        // Safety check: return null if input is null
        if (notification == null) {
            return null;
        }

        // Create DTO with all necessary fields
        return new NotificationDTO(
                notification.getNotificationID(),
                notification.getMessage(),
                notification.getDateSent(),
                notification.getStatus().toString(),
                notification.getUser() != null ? notification.getUser().getUserId() : null,  // User ID (nullable)
                notification.getUser() != null ? notification.getUser().getName() : null      // User name (nullable)
        );
    }


    public Notification toDomain(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        // Convert String status to BookingStatus enum
        BookingStatus status = null;
        if (dto.getStatus() != null) {
            try {
                status = BookingStatus.valueOf(dto.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                // If invalid status, default to PENDING
                status = BookingStatus.PENDING;
            }
        }

        // Fetch the User from database using userId
        User user = null;
        if (dto.getUserId() != null) {
            user = userService.read(dto.getUserId());
        }

        // Create domain object using builder pattern
        return new Notification.Builder()
                .setNotificationID(dto.getNotificationID())
                .setMessage(dto.getMessage())
                .setDateSent(dto.getDateSent())
                .setStatus(status)
                .setUserID(user)  // Set the fetched User object
                .build();
    }


    public List<NotificationDTO> toDTOList(List<Notification> notifications) {
        // Safety check: return null if input is null
        if (notifications == null) {
            return null;
        }

        // Use stream API to convert each notification to DTO
        return notifications.stream()
                .map(this::toDTO)  // Apply toDTO method to each element
                .collect(Collectors.toList());   // Collect results into a new list
    }


    public List<Notification> toDomainList(List<NotificationDTO> dtos) {
        // Safety check: return null if input is null
        if (dtos == null) {
            return null;
        }

        // Use stream API to convert each DTO to domain object
        return dtos.stream()
                .map(this::toDomain)  // Apply toDomain method to each element
                .collect(Collectors.toList());      // Collect results into a new list
    }
}