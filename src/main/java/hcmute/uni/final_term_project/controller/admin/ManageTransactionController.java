package hcmute.uni.final_term_project.controller.admin;

import hcmute.uni.final_term_project.entity.Promo;
import hcmute.uni.final_term_project.entity.Subscription;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.SubscriptionRepository;
import org.springframework.ui.Model;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class ManageTransactionController {
    @Autowired
    private UserService userService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private PromoService promoService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private LikesService likesService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @GetMapping("/admin/transaction")
    public String adminTransaction(Model model) {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();

        // Đổ dữ liệu vào model để hiển thị trong Thymeleaf
        model.addAttribute("subscriptions", subscriptions);
        long totalSubscribers = subscriptions.size();

        // Tính tổng doanh thu
        double totalRevenue = subscriptions.stream()
                .mapToDouble(Subscription::getValue) // Lấy giá trị value của mỗi subscription
                .sum();

        // Đổ dữ liệu vào model
        model.addAttribute("totalSubscribers", totalSubscribers);
        model.addAttribute("totalRevenue", totalRevenue);

        return "admin/manage-transaction";
    }
}
