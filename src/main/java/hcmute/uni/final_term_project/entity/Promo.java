package hcmute.uni.final_term_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promoId;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Double value;

    private int uses;
    private int limitUsage;

    @Column(nullable = false, unique = true)
    private String code;
}
