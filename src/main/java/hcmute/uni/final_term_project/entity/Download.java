package hcmute.uni.final_term_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Download")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long downloadId;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "doc_id", nullable = false)
    private Document document;
}

