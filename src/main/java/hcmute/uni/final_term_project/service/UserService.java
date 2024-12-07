package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Tìm người dùng theo ID
    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
        return userRepository.findById(id);
    }

    // Tìm người dùng theo email
    public User getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        return userRepository.findByEmail(email);
    }

    // Tìm kiếm người dùng theo tên (keyword)
    public List<User> searchUsersByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty.");
        }
        return userRepository.findByNameContaining(keyword);
    }

    // Tạo mới hoặc cập nhật thông tin người dùng
    public User saveOrUpdateUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    // Xóa người dùng theo ID
    public void deleteUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }

    // Đếm số người dùng đang hoạt động
    public long countActiveUsers() {
        return userRepository.countByIsActiveTrue();
    }

    // Kích hoạt hoặc vô hiệu hóa người dùng
    public void setActiveStatus(Long userId, boolean isActive) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        User user = userOptional.get();
        user.setActive(isActive);
        userRepository.save(user);
    }

    // Cập nhật thời gian đăng nhập cuối cùng
    public void updateLastLogin(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        User user = userOptional.get();
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    // Kiểm tra thông tin người dùng hợp lệ
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("User email cannot be null or empty.");
        }
        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("User email must be a valid email address.");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
    }

    // Kiểm tra xem người dùng có phải là admin hay không
    public boolean isAdmin(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        User user = userOptional.get();
        return user.isAdmin();
    }

    // Kiểm tra xem người dùng có phải là VIP hay không
    public boolean isVIP(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        User user = userOptional.get();
        return user.isVIP();
    }
}