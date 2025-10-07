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
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.CreateNotificationDTO;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.service.authentication.UserService;
import za.co.carhire.service.reservation.IBookingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
    // Convert Notification domain object to NotificationDTO
    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) {
            return null;
        }
        if (notification.getUser() == null) {
            throw new IllegalStateException("Notification must have a user");
        }
        return new NotificationDTO(
                notification.getNotificationID(),
                notification.getUser().getUserId(),
                notification.getMessage(),
                notification.isReadStatus(),
                notification.getDateSent());

    }

    // Convert CreateNotificationDTO to Notification domain object
    public static Notification toDomain(CreateNotificationDTO createNotificationDTO, User user) {
        return new Notification.Builder()
                .setUser(user)
                .setMessage(createNotificationDTO.message())
                .setReadStatus(false)
                .setDateSent(LocalDateTime.now())
                .build();
    }

}