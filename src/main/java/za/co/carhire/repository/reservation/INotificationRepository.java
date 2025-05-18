package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.carhire.domain.reservation.Notification;

public interface INotificationRepository extends JpaRepository<Notification, Integer> {
}
