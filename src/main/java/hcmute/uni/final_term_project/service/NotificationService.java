package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Notification;
import hcmute.uni.final_term_project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Get all notifications
     *
     * @return List of notifications
     */
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * Get a notification by its ID
     *
     * @param id Notification ID
     * @return Optional Notification
     */
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    /**
     * Create a new notification
     *
     * @param notification Notification object to be created
     * @return The saved Notification
     */
    public Notification createNotification(Notification notification) {
        notification.setSentAt(LocalDateTime.now()); // Set current timestamp for `sentAt`
        return notificationRepository.save(notification);
    }

    /**
     * Update an existing notification
     *
     * @param id Notification ID
     * @param notification Updated notification details
     * @return The updated Notification
     */
    public Notification updateNotification(Long id, Notification notification) {
        Optional<Notification> existingNotification = notificationRepository.findById(id);

        if (existingNotification.isPresent()) {
            Notification updatedNotification = existingNotification.get();
            updatedNotification.setTitle(notification.getTitle());
            updatedNotification.setContent(notification.getContent());
            updatedNotification.setSender(notification.getSender());
            updatedNotification.setSentAt(LocalDateTime.now()); // Update timestamp
            return notificationRepository.save(updatedNotification);
        }

        throw new RuntimeException("Notification not found with ID: " + id);
    }

    /**
     * Delete a notification by its ID
     *
     * @param id Notification ID
     */
    public void deleteNotificationById(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Notification not found with ID: " + id);
        }
    }
}
