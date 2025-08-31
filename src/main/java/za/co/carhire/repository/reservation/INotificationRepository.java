package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Notification;

import java.util.List;
/* INotifricationRepository.java

     INotificationRepository/repository class

     Author: Bonga Velem

     Student Number: 220052379

     */
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Integer> {
  List<Notification> findByUser_IdNumber(Long idNumber);

  List<Notification> findByUser_IdNumberAndStatus(Long idNumber, String status);
}
