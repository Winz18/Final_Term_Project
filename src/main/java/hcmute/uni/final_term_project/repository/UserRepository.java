package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // Tìm user qua email
    List<User> findByNameContaining(String keyword); // Tìm user theo tên
    long countByIsActiveTrue(); // Đếm số user đang hoạt động
    long countByIsVIPTrue(); // Đếm số user VIP


    long countByIsActive(boolean b);
}
