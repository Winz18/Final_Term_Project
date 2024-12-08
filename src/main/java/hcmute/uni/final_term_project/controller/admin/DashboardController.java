package hcmute.uni.final_term_project.controller.admin;

import hcmute.uni.final_term_project.repository.CommissionRepository;
import hcmute.uni.final_term_project.repository.DocumentRepository;
import hcmute.uni.final_term_project.repository.UserRepository;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


@Controller
public class DashboardController {
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
    private CommissionService commissionService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    CommissionRepository commissionRepository;
    @GetMapping("/admin")
    public String GotoDashboard() {
        return "admin/dashboard"; // file templates/index.html
    }
    @GetMapping("/admin/dashboard")
    public String Dashboard(Model model) {
        // Thống kê số lượng Users
        long totalUsers = userService.countAllUsers();
        long vipUsers = userService.countVipUsers();
        long onlineUsers = userService.countOnlineUsers();

        // Thống kê số lượng Documents
        long totalDocuments = documentRepository.count();
        long todaysUploads = documentRepository.countDocumentsUploadedToday();

        // Thống kê doanh thu
        long dailyRevenue = commissionRepository.getDailyRevenue();
        long monthlyRevenue = commissionRepository.getMonthlyRevenue();
        long yearlyRevenue = commissionRepository.getYearlyRevenue();

        // Đưa dữ liệu vào model
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("vipUsers", vipUsers);
        model.addAttribute("onlineUsers", onlineUsers);
        model.addAttribute("totalDocuments", totalDocuments);
        model.addAttribute("todaysUploads", todaysUploads);
        model.addAttribute("dailyRevenue", dailyRevenue);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("yearlyRevenue", yearlyRevenue);

        return "admin/dashboard"; // file templates/index.html
    }
}
