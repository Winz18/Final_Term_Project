package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Commission;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long> {
    List<Commission> findByUser(User user);
    List<Commission> findByDocument(Document document);
    List<Commission> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    // Lấy danh sách hoa hồng theo khoảng thời gian
    @Query("SELECT SUM(c.value) FROM Commission c WHERE c.user = :user AND c.date BETWEEN :startDate AND :endDate")
    Double sumValueByUserAndDateBetween(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
