package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Subscription;
import hcmute.uni.final_term_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByUser(User user); // Lấy gói subscription của user
    List<Subscription> findByEndDateAfter(LocalDateTime now); // Tìm các user đang trong gói VIP

}
