package hcmute.uni.final_term_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "view_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewHistory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long viewHistoryId;

        @ManyToOne
        @JoinColumn(name = "document_id")
        private Document document;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @Column(name = "viewed_at")
        private LocalDateTime viewedAt;
}
