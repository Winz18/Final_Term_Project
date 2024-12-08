package hcmute.uni.final_term_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId; // Notification ID

    @Column(nullable = false)
    private String title; // Title of the notification

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // Content of the notification

    @Column(nullable = false)
    private String sender; // Sender of the notification

    @Column(nullable = false)
    private LocalDateTime sentAt; // Timestamp when the notification was sent
}
