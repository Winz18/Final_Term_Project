package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // Tìm user qua email
    List<User> findByNameContaining(String keyword); // Tìm user theo tên
    long countByIsActiveTrue(); // Đếm số user đang hoạt động
    long countByIsVIPTrue(); // Đếm số user VIP
    long countByIsActive(boolean b);
  
    // Lấy danh thu trong tháng của người dùng hiện tại
    @Query("SELECT SUM(c.value) FROM Commission c WHERE c.user.userId = :userId " +
            "AND c.date BETWEEN :startDate AND :endDate")
    Double sumCommissionValue(@Param("userId") Long userId,
                              @Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

    // Đếm số lượng follower của người dùng
    @Query("SELECT COUNT(f) FROM Follower f WHERE f.followed.userId = :userId")
    Long countFollowers(@Param("userId") Long userId);

    // Đếm số lượng follower mới của người dùng trong tháng
    @Query("SELECT COUNT(f) FROM Follower f WHERE f.followed.userId = :userId " +
            "AND f.time BETWEEN :startOfMonth AND :endOfMonth")
    long countNewFollowers(Long userId, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    // Đếm số lượng tài liệu đã upload trong tháng
    @Query("SELECT COUNT(d) FROM Document d WHERE d.owner.userId = :userId " +
            "AND d.dateUploaded BETWEEN :startOfMonth AND :endOfMonth")
    long countDocumentUploaded(Long userId, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    // Đếm số lượng tài liệu mà người dùng đã upload
    @Query("SELECT COUNT(d) FROM Document d WHERE d.owner.userId = :userId")
    long countByDocumentsUploaded(Long userId);
}
