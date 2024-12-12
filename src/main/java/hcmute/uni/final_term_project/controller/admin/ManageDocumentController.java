package hcmute.uni.final_term_project.controller.admin;

import hcmute.uni.final_term_project.repository.DocumentRepository;
import hcmute.uni.final_term_project.repository.LikesRepository;
import org.springframework.ui.Model;
import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ManageDocumentController {
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
    private DocumentService documentService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private LikesRepository likeRepository;
    @GetMapping("/admin/documents")
    public String adminDocuments(Model model) {
        List<Document> documents = documentService.getAllDocuments();

        // Truyền danh sách tài liệu vào model để hiển thị trên Thymeleaf
        model.addAttribute("documents", documents);
        List<String> tags = documentService.getAllTags();

        // Chuyển danh sách các tag vào model
        model.addAttribute("tags", tags);

        return "admin/manage-document";
    }
    @PostMapping("admin/documents/edit")
    public String editDocument(Model model,
            @RequestParam Long docId,
            @RequestParam String background,
            @RequestParam String university,
            @RequestParam String cateTags,
            @RequestParam int views,
            RedirectAttributes redirectAttributes) {
        try {
            // Gọi service để cập nhật tài liệu
            Optional<Document> documentOptional = documentService.getDocumentById(docId);
            documentOptional.get().setBackground(background);
            documentOptional.get().setUniversity(university);
            documentOptional.get().setCateTags(cateTags);
            documentOptional.get().setViews(views);
            documentService.saveOrUpdateDocument(documentOptional.get());
            redirectAttributes.addFlashAttribute("successMessage", "Document updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update document. Please try again.");
        }
        return "redirect:/admin/documents";
    }
    @PostMapping("admin/documents/delete")
    public String deleteDocument(@RequestParam Long docId, RedirectAttributes redirectAttributes) {
        try {
            documentService.deleteDocumentById(docId);
            redirectAttributes.addFlashAttribute("successMessage", "Document deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete document. Please try again.");
        }
        return "redirect:/admin/documents";
    }
    @PostMapping("/admin/documents/approve")
    public String approveDocument(@RequestParam Long docId, RedirectAttributes redirectAttributes) {
        try {
            documentService.approveDocument(docId);
            redirectAttributes.addFlashAttribute("successMessage", "Document deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete document. Please try again.");
        }
        return "redirect:/admin/documents";
    }
}
