package hcmute.uni.final_term_project.controller.user;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.entity.ViewHistory;
import hcmute.uni.final_term_project.service.DocumentService;
import hcmute.uni.final_term_project.service.UserService;
import hcmute.uni.final_term_project.service.ViewHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@RequestMapping("/user")
public class UserNavigationController {

    private final DocumentService documentService;
    private final UserService userService;
    private final ViewHistoryService viewHistoryService;

    @Autowired
    public UserNavigationController(DocumentService documentService,
                                    UserService userService, ViewHistoryService viewHistoryService) {
        this.documentService = documentService;
        this.userService = userService;
        this.viewHistoryService = viewHistoryService;
    }

    // Controller for user home page
    @GetMapping("/home")
    public String showHomePage(Model model) {
        // Lấy danh sách tài liệu đã xem gần đây (không trùng lặp)
        List<ViewHistory> recentViewedHistories = viewHistoryService.getTop3DistinctViewHistoryByUser(userService.getCurrentUser());
        List<Document> recentDocuments = recentViewedHistories.stream().map(ViewHistory::getDocument).toList();
        model.addAttribute("recentDocuments", recentDocuments);

        // Lấy danh sách tài liệu được đề xuất
        List<Document> recommendedDocuments = documentService.getRecommendedDocuments();
        model.addAttribute("recommendedDocuments", recommendedDocuments);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("commission", userService.getCommissionThisMonth());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("newFollowers", userService.countNewFollowersThisMonth());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());
        model.addAttribute("documentsUploadedThisMonth", userService.countDocumentUploadedThisMonth());

        return "user/user-home";
    }

    @GetMapping("/search")
    public String searchDocument(@RequestParam("query") String query, Model model) {
        List<Document> documents = documentService.searchDocumentsByName(query);
        model.addAttribute("documents", documents);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/search";
    }

    // Controller for recent viewed documents
    @GetMapping("/recent-documents")
    public String showRecentViewedDocuments(Model model) {
        // Lấy danh sách tài liệu đã xem gần đây
        List<ViewHistory> recentViewedHistories = viewHistoryService.getDistinctViewHistoryByUser(userService.getCurrentUser());
        List<Document> recentDocuments = recentViewedHistories.stream().map(ViewHistory::getDocument).toList();
        model.addAttribute("recentDocuments", recentDocuments);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/recent-documents";
    }

    // Controller for recommended documents
    @GetMapping("/recommended-documents")
    public String showRecommendedDocuments(Model model) {
        // Lấy danh sách tài liệu được đề xuất
        List<Document> recommendedDocuments = documentService.getRecommendedDocuments();
        model.addAttribute("recommendedDocuments", recommendedDocuments);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/recommend-documents";
    }


}
