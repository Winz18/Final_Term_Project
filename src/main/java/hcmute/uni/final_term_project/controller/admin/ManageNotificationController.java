package hcmute.uni.final_term_project.controller.admin;

import hcmute.uni.final_term_project.entity.Notification;
import org.springframework.ui.Model;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ManageNotificationController {
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
    private NotificationService notificationService;
    @GetMapping("/admin/notification")
    public String notification(Model model) {
        List<Notification> notifications = notificationService.getAllNotifications();
        // Add the notifications list to the model to pass to the view
        model.addAttribute("notifications", notifications);
        return "admin/manage-notification";
    }
    @PostMapping("/admin/notification/add")
    public String addNotification(@RequestParam("title") String title,
                                  @RequestParam("content") String content){
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setContent(content);
        notification.setSender("Admin"); // Default sender
        notification.setSentAt(LocalDateTime.now()); // Current timestamp
        notificationService.createNotification(notification);
        return "redirect:/admin/notification";
    }
    @PostMapping("admin/notification/delete")
    public String deleteNotification(@RequestParam("notificationId") Long notificationId) {
        // Delete the notification by ID
        notificationService.deleteNotificationById(notificationId);
        return "redirect:/admin/notification"; // Redirect to the notifications page
    }
}
