package hcmute.uni.final_term_project.controller.user;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.entity.ViewHistory;
import hcmute.uni.final_term_project.entity.Subscription;
import hcmute.uni.final_term_project.service.DocumentService;
import hcmute.uni.final_term_project.service.UserService;
import hcmute.uni.final_term_project.service.ViewHistoryService;
import hcmute.uni.final_term_project.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserPaymentController {
    private final UserService userService;
    private final DocumentService documentService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public UserPaymentController(UserService userService , DocumentService documentService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.documentService = documentService;
        this.subscriptionService = subscriptionService;

    }

    @GetMapping("/vip-member")
    public String showVipMemberPage(Model model) {
        // get vip plan of current user
        Subscription currentSubscription = subscriptionService.getSubscriptionByUser(userService.getCurrentUser());
        model.addAttribute("currentSubscription", currentSubscription);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("bio", userService.getCurrentUser().getBio());
        model.addAttribute("email", userService.getCurrentUser().getEmail());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());
        model.addAttribute("commission", userService.getCommissionThisMonth());

        return "user/vip-member";
    }

    // upgrade to vip
    @GetMapping("/payment")
    public String showPaymentPage(Model model) {
        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("bio", userService.getCurrentUser().getBio());
        model.addAttribute("email", userService.getCurrentUser().getEmail());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());
        model.addAttribute("commission", userService.getCommissionThisMonth());

        return "user/payment";
    }
}
