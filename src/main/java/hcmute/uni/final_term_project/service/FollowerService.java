package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Follower;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    private final FollowerRepository followerRepository;

    @Autowired
    public FollowerService(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    // Lấy tất cả các lượt theo dõi
    public List<Follower> getAllFollowers() {
        return followerRepository.findAll();
    }

    // Tìm lượt theo dõi bằng ID
    public Optional<Follower> getFollowerById(Long followId) {
        if (followId == null || followId <= 0) {
            throw new IllegalArgumentException("Follow ID must be a positive number.");
        }
        return followerRepository.findById(followId);
    }

    // Lấy danh sách người dùng mà user đang theo dõi
    public List<Follower> getFollowings(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return followerRepository.findByFollower(user);
    }

    // Lấy danh sách người đang theo dõi user
    public List<Follower> getFollowers(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return followerRepository.findByFollowed(user);
    }

    // Đếm số người dùng mà user đang theo dõi
    public long countFollowings(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return followerRepository.countByFollower(user);
    }

    // Đếm số người đang theo dõi user
    public long countFollowers(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return followerRepository.countByFollowed(user);
    }

    // Kiểm tra xem user có theo dõi người khác không
    public boolean isFollowing(User follower, User followed) {
        if (follower == null || follower.getUserId() == null) {
            throw new IllegalArgumentException("Follower cannot be null or without an ID.");
        }
        if (followed == null || followed.getUserId() == null) {
            throw new IllegalArgumentException("Followed cannot be null or without an ID.");
        }
        return followerRepository.existsByFollowerAndFollowed(follower, followed);
    }

    // Thêm lượt theo dõi
    public Follower addFollower(User follower, User followed) {
        if (follower == null || follower.getUserId() == null) {
            throw new IllegalArgumentException("Follower cannot be null or without an ID.");
        }
        if (followed == null || followed.getUserId() == null) {
            throw new IllegalArgumentException("Followed cannot be null or without an ID.");
        }
        if (isFollowing(follower, followed)) {
            throw new IllegalArgumentException("User is already following this person.");
        }

        Follower newFollower = new Follower();
        newFollower.setFollower(follower);
        newFollower.setFollowed(followed);
        newFollower.setTime(LocalDateTime.now());

        return followerRepository.save(newFollower);
    }

    // Hủy theo dõi
    public void removeFollower(User follower, User followed) {
        if (follower == null || follower.getUserId() == null) {
            throw new IllegalArgumentException("Follower cannot be null or without an ID.");
        }
        if (followed == null || followed.getUserId() == null) {
            throw new IllegalArgumentException("Followed cannot be null or without an ID.");
        }
        if (!isFollowing(follower, followed)) {
            throw new IllegalArgumentException("User is not following this person.");
        }

        Follower followRecord = followerRepository.findByFollower(follower).stream()
                .filter(f -> f.getFollowed().getUserId().equals(followed.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Follow record not found."));
        followerRepository.delete(followRecord);
    }
}
