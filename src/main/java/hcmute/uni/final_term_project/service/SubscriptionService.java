package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Promo;
import hcmute.uni.final_term_project.entity.Subscription;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.SubscriptionRepository;
import hcmute.uni.final_term_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    // Lấy tất cả subscription
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    // Lấy subscription của một user
    public Subscription getSubscriptionByUser(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return subscriptionRepository.findByUser(user);
    }

    // Tìm subscription theo ID
    public Optional<Subscription> getSubscriptionById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Subscription ID must be a positive number.");
        }
        return subscriptionRepository.findById(id);
    }

    // Tạo mới hoặc cập nhật subscription
    public Subscription saveOrUpdateSubscription(Subscription subscription) {
        validateSubscription(subscription);
        return subscriptionRepository.save(subscription);
    }

    // Xóa subscription theo ID
    public void deleteSubscriptionById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Subscription ID must be a positive number.");
        }
        if (!subscriptionRepository.existsById(id)) {
            throw new IllegalArgumentException("Subscription with ID " + id + " does not exist.");
        }
        subscriptionRepository.deleteById(id);
    }

    // Tìm các subscription hiện đang hoạt động
    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findByEndDateAfter(LocalDateTime.now());
    }

    // Kiểm tra xem một user có đang trong gói VIP hay không
    public boolean isUserVIP(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        Subscription subscription = subscriptionRepository.findByUser(user);
        return subscription != null && subscription.getEndDate().isAfter(LocalDateTime.now());
    }

    // Gia hạn gói subscription
    public Subscription extendSubscription(Long subscriptionId, int additionalMonths) {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("Subscription ID must be a positive number.");
        }
        if (additionalMonths <= 0) {
            throw new IllegalArgumentException("Additional months must be greater than 0.");
        }

        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);
        if (optionalSubscription.isEmpty()) {
            throw new IllegalArgumentException("Subscription with ID " + subscriptionId + " does not exist.");
        }

        Subscription subscription = optionalSubscription.get();
        subscription.setEndDate(subscription.getEndDate().plusMonths(additionalMonths));
        return subscriptionRepository.save(subscription);
    }

    // Validate subscription
    private void validateSubscription(Subscription subscription) {
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription cannot be null.");
        }
        if (subscription.getUser() == null || subscription.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Subscription must be linked to a valid user.");
        }
        if (subscription.getStartDate() == null || subscription.getEndDate() == null) {
            throw new IllegalArgumentException("Subscription must have valid start and end dates.");
        }
        if (subscription.getEndDate().isBefore(subscription.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        if (subscription.getType() == null || (!subscription.getType().equalsIgnoreCase("monthly") && !subscription.getType().equalsIgnoreCase("yearly"))) {
            throw new IllegalArgumentException("Subscription type must be either 'monthly' or 'yearly'.");
        }
    }
}
