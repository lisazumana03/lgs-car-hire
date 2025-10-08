package za.co.carhire.service.reservation;
/* NotificationService.java

     Notification reservation/Service interface

     Author: Bonga Velem

     Student Number: 220052379

     */
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.dto.CreateNotificationDTO;
import za.co.carhire.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO sendNotification(CreateNotificationDTO createDTO);


    List<NotificationDTO> getUserNotifications(int userId);


    NotificationDTO markAsRead(int notificationId);
}
