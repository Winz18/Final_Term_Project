package hcmute.uni.final_term_project.repository;

import hcmute.uni.final_term_project.entity.Follower;
import hcmute.uni.final_term_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follower, Long> {

    // Tìm tất cả những người dùng mà user đang theo dõi
    List<Follower> findByFollower(User follower);

    // Tìm tất cả những người theo dõi user
    List<Follower> findByFollowed(User followed);

    long countByFollower(User follower); // Số lượng người user đang theo dõi
    long countByFollowed(User followed); // Số lượng người theo dõi user

    // Kiểm tra xem user có đang theo dõi người khác không
    boolean existsByFollowerAndFollowed(User follower, User followed);


}
