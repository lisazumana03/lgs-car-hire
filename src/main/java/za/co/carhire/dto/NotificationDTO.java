package za.co.carhire.dto;

import java.time.LocalDateTime;

public record NotificationDTO(
        int notificationID,
        int userId,
        String message,
        boolean readStatus,
        LocalDateTime dateSent
) {
}
