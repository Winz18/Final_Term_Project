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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Arrays;
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

        // filter out deleted documents
        List<Document> filteredRecentDocuments = recentDocuments.stream().filter(document -> !document.getCateTags().equals("del")).toList();

        // loại bỏ các tài liệu chưa được duyệt
        filteredRecentDocuments = filteredRecentDocuments.stream().filter(Document::isApproved).toList();

        model.addAttribute("recentDocuments", filteredRecentDocuments);

        // Lấy danh sách tài liệu được đề xuất
        List<Document> recommendedDocuments = documentService.getRecommendedDocuments();

        // filter out deleted documents
        List<Document> filteredRecommendedDocuments = recommendedDocuments.stream().filter(document -> !document.getCateTags().equals("del")).toList();

        // loại bỏ các tài liệu chưa được duyệt
        filteredRecommendedDocuments = filteredRecommendedDocuments.stream().filter(Document::isApproved).toList();

        model.addAttribute("recommendedDocuments", filteredRecommendedDocuments);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("commission", userService.getCommissionThisMonth());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("newFollowers", userService.countNewFollowersThisMonth());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());
        model.addAttribute("documentsUploadedThisMonth", userService.countDocumentUploadedThisMonth());

        // Nếu là admin thì redirect sang trang admin
        if (userService.getCurrentUser().isAdmin()) {
            return "redirect:/admin";
        }

        return "user/user-home";
    }

    @GetMapping("/search")
    public String searchDocument(@RequestParam("query") String query, Model model) {
        List<Document> documents = documentService.searchDocumentsByName(query);

        // filter out deleted documents
        List<Document> filteredDocuments = documents.stream().filter(document -> !document.getCateTags().equals("del")).toList();

        // loại bỏ các tài liệu chưa được duyệt
        filteredDocuments = filteredDocuments.stream().filter(Document::isApproved).toList();

        model.addAttribute("documents", filteredDocuments);

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

        // filter out deleted documents
        List<Document> filteredRecentDocuments = recentDocuments.stream().filter(document -> !document.getCateTags().equals("del")).toList();

        // loại bỏ các tài liệu chưa được duyệt
        filteredRecentDocuments = filteredRecentDocuments.stream().filter(Document::isApproved).toList();

        model.addAttribute("recentDocuments", filteredRecentDocuments);

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

        // filter out deleted documents
        List<Document> filteredRecommendedDocuments = recommendedDocuments.stream().filter(document -> !document.getCateTags().equals("del")).toList();

        // loại bỏ các tài liệu chưa được duyệt
        filteredRecommendedDocuments = filteredRecommendedDocuments.stream().filter(Document::isApproved).toList();

        model.addAttribute("recommendedDocuments", filteredRecommendedDocuments);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/recommend-documents";
    }

    // endpoint for cate-doc page
    @GetMapping("/cate-doc")
    public String searchByTag (Model model) {
        // get popular tags
        List<String> popularTags = documentService.getPopularTags();
        model.addAttribute("popularTags", popularTags);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/cate-doc";
    }

    // endpoint for cate-search page
    @GetMapping("/cate-search")
    public String searchByTag(@RequestParam("tag") String tag, Model model) {
        // get documents by tag
        List<Document> documents = documentService.getDocumentsByTag(tag);

        // filter out deleted documents
        List<Document> filteredDocuments = documents.stream().filter(document -> !document.getCateTags().equals("del")).toList();

        // loại bỏ các tài liệu chưa được duyệt
        filteredDocuments = filteredDocuments.stream().filter(Document::isApproved).toList();

        model.addAttribute("documents", filteredDocuments);

        model.addAttribute("tag", tag);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/cate-search";
    }

    // endpoint for uni-doc page
    @GetMapping("/uni-doc")
    public String searchByUniversity (Model model) {
        // get universities
        List<String> universities = documentService.getPopularUniversities();
        model.addAttribute("universities", universities);

        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/uni-doc";
    }

    // endpoint for uni-search page
    @GetMapping("/uni-search")
    public String searchByUniversity(@RequestParam("uni") String university, Model model) {
        // get documents by university
        List<Document> documents = documentService.getDocumentsByUniversity(university);

        // filter out deleted documents
        List<Document> filteredDocuments = documents.stream().filter(document -> !document.getCateTags().equals("del")).toList();

        // loại bỏ các tài liệu chưa được duyệt
        filteredDocuments = filteredDocuments.stream().filter(Document::isApproved).toList();

        model.addAttribute("documents", filteredDocuments);

        model.addAttribute("university", university);
        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/uni-search";
    }
}
