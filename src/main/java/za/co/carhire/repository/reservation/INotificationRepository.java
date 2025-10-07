package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Notification;

import java.util.List;
/* INotifricationRepository.java

     INotificationRepository/repository class

     Author: Bonga Velem

     Student Number: 220052379

     */
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Integer> {
  // Find all notifications for a user
  List<Notification> findByUser(User user);

  // Find unread notifications for a user
  List<Notification> findByUserAndReadStatus(User user, boolean readStatus);
}
