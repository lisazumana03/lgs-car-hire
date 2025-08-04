package za.co.carhire.service.impl.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.repository.reservation.INotificationRepository;
import za.co.carhire.service.reservation.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final INotificationRepository repository;

    @Autowired
    public NotificationServiceImpl(INotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public Notification read(Integer notificationId) {
        return repository.findById(notificationId).orElse(null);
    }

    @Override
    public Notification update(Notification notification) {
        if (repository.existsById(notification.getNotificationID())) {
            return repository.save(notification);
        }
        return null;
    }

    @Override
    public void delete(Integer notificationId) {
        repository.deleteById(notificationId);
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        return repository.findByUser_IdNumber(userId);
    }

    @Override
    public List<Notification> findByUserIdAndStatus(Long userId, String status) {
        return repository.findByUser_IdNumberAndStatus(userId, status);
    }
}
