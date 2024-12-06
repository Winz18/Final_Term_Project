package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Commission;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long> {
    List<Commission> findByUser(User user);
    List<Commission> findByDocument(Document document);

    List<Commission> findByUserAndDateBetween(User user, LocalDate start, LocalDate end); // Lấy danh sách hoa hồng theo khoảng thời gian
    double sumValueByUserAndDateBetween(User user, LocalDate start, LocalDate end); // Tổng doanh thu của user trong khoảng thời gian

}
