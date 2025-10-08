package za.co.carhire.service.reservation.impl;

/* NotificationServiceImpl.java

     Notification reservation/Service class

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.dto.CreateNotificationDTO;
import za.co.carhire.dto.NotificationDTO;
import za.co.carhire.mapper.NotificationMapper;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.reservation.INotificationRepository;
import za.co.carhire.service.reservation.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final INotificationRepository repository;

    private final IUserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(INotificationRepository repository, IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @Override
    public NotificationDTO sendNotification(CreateNotificationDTO createDTO) {
        User user = userRepository.findById(createDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + createDTO.userId()));

        Notification notification = NotificationMapper.toDomain(createDTO, user);
        return NotificationMapper.toDTO(repository.save(notification));
    }

    @Override
    public List<NotificationDTO> getUserNotifications(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return repository.findByUser(user).stream()
                .map(NotificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO markAsRead(int notificationId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with ID: " + notificationId));

        Notification updated = new Notification.Builder()
                .copy(notification)
                .setReadStatus(true)
                .build();

        return NotificationMapper.toDTO(repository.save(updated));
    }
}
