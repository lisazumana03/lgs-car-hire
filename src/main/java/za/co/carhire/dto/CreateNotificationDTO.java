package za.co.carhire.dto;

import java.time.LocalDateTime;

public record CreateNotificationDTO(
        int userId,
        String message
) {
}
