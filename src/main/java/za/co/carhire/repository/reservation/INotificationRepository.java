package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Notification;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Integer> {
}
