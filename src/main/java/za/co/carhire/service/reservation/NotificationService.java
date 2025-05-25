package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Notification;

import java.util.List;

public interface NotificationService {
    Notification save(Notification notification);
    Notification read(Integer notificationId);
    Notification update(Notification notification);
    void delete(Integer notificationId);
    List<Notification> findAll();

    List<Notification> findByUserId(Integer userId);
    List<Notification> findByUserIdAndStatus(Integer userId, String status);
}
