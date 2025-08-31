package za.co.carhire.mapper;
/* NotifricationMapper.java

     NotificationMapper/mapper class

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.service.authentication.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationMapper {

    @Autowired
    private UserService userService;

    public NotificationDTO toDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        return new NotificationDTO(
                notification.getNotificationID(),
                notification.getMessage(),
                notification.getDateSent(),
                notification.getStatus().toString(),
                notification.getUser() != null ? notification.getUser().getUserId() : null,
                notification.getUser() != null ? notification.getUser().getName() : null
        );
    }

    public Notification toDomain(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        BookingStatus status = null;
        if (dto.getStatus() != null) {
            try {
                status = BookingStatus.valueOf(dto.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                status = BookingStatus.PENDING;
            }
        }

        User user = null;
        if (dto.getUserId() != null) {
            user = userService.read(dto.getUserId());
        }

        return new Notification.Builder()
                .setNotificationID(dto.getNotificationID())
                .setMessage(dto.getMessage())
                .setDateSent(dto.getDateSent())
                .setStatus(status)
                .setUserID(user)
                .build();
    }

    public List<NotificationDTO> toDTOList(List<Notification> notifications) {
        if (notifications == null) {
            return null;
        }

        return notifications.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Notification> toDomainList(List<NotificationDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}