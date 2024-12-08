package hcmute.uni.final_term_project.controller.admin;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.Download;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.CommissionRepository;
import hcmute.uni.final_term_project.repository.DocumentRepository;
import hcmute.uni.final_term_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import hcmute.uni.final_term_project.entity.Commission;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ManageCommisionController {
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
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    CommissionRepository commissionRepository;
    @GetMapping("/admin/commision")
    public String adminCommision(Model model) {
        List<Commission> commissions = commissionService.getAllCommissions();
        List<Download> downloads = downloadService.getAllDownloads();
        model.addAttribute("commissions", commissions);


        // Tính toán các giá trị tổng hợp
        double totalCommission = commissions.stream()
                .mapToDouble(Commission::getValue)
                .sum();

        double approvedCommission = commissions.stream()
                .filter(Commission::getPaid)
                .mapToDouble(Commission::getValue)
                .sum();

        double pendingCommission = totalCommission - approvedCommission;

        // Truyền các giá trị vào model
        model.addAttribute("totalCommission", totalCommission);
        model.addAttribute("approvedCommission", approvedCommission);
        model.addAttribute("pendingCommission", pendingCommission);
        return "admin/manage-commision";
    }
    @PostMapping("/admin/commision/approve")
    public String approvePayment(@RequestParam Long commissionId){
        try {
            Commission commission = commissionRepository.findById(commissionId).get();
            commission.setPaid(true);
            commissionService.saveOrUpdateCommission(commission);
            return "redirect:/admin/commision";
        } catch (Exception e) {
            return "redirect:/admin/commision";
        }
    }
    @PostMapping("/admin/commision/payall")
    public String payall(){
        List<Commission> pendingCommissions = commissionRepository.findByPaid(false);
        pendingCommissions.forEach(commission -> commission.setPaid(true));
        commissionRepository.saveAll(pendingCommissions);
        return "redirect:/admin/commision";
    }
}
