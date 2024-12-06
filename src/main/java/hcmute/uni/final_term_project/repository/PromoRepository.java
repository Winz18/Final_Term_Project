package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
    Promo findByCode(String code); // Tìm promo bằng mã
    List<Promo> findByEndDateAfterAndStartDateBefore(LocalDateTime endDate, LocalDateTime startDate); // Lấy các mã khuyến mãi đang áp dụng
}
