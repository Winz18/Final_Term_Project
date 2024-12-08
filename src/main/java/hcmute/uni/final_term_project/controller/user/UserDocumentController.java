package hcmute.uni.final_term_project.controller.user;

import hcmute.uni.final_term_project.entity.Comment;
import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.Download;
import hcmute.uni.final_term_project.entity.Likes;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserDocumentController {

    private final DocumentService documentService;
    private final UserService userService;
    private final ViewHistoryService viewHistoryService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final DownloadService downloadService;

    @Autowired
    public UserDocumentController(DocumentService documentService,
                                  UserService userService,
                                  ViewHistoryService viewHistoryService,
                                  LikesService likesService,
                                  CommentService commentService,
                                  DownloadService downloadService) {
        this.documentService = documentService;
        this.userService = userService;
        this.viewHistoryService = viewHistoryService;
        this.likesService = likesService;
        this.commentService = commentService;
        this.downloadService = downloadService;
    }

    // endpoint for user document page
    @GetMapping("/my-documents")
    public String showMyDocumentPage(Model model) {
        // Lấy danh sách tài liệu do user hiện tại sở hữu
        List<Document> myDocuments = documentService.getDocumentsByOwner(userService.getCurrentUser());
        model.addAttribute("myDocuments", myDocuments);

        // Get info for sidebar
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/my-documents";
    }

    // endpoint for view detail of a document
    @GetMapping("/view-detail/{id}")
    public String showDocumentDetailPage(@PathVariable("id") Long documentId, Model model) {
        // Lấy tài liệu theo ID
        Optional<Document> document = documentService.getDocumentById(documentId);
        Document doc = document.orElseThrow(() -> new IllegalArgumentException("Document not found"));

        model.addAttribute("document", doc);

        // Lấy danh sách người dùng đã like tài liệu này
        List<Likes> likes = likesService.getLikesByDocument(doc);
        model.addAttribute("likes", likes.size());

        // Lấy danh sách bình luận của tài liệu
        List<Comment> comments = commentService.getCommentsByDocument(doc);
        model.addAttribute("comments", comments);

        // Lấy danh sách người dùng đã tải tài liệu này
        List<Download> downloads = downloadService.getDownloadsByDocument(doc);
        model.addAttribute("downloads", downloads.size());

        // Kiểm tra xem người dùng hiện có phải là chủ sở hữu của tài liệu hay không
        model.addAttribute("isOwner", doc.getOwner().getUserId().equals(userService.getCurrentUser().getUserId()));

        // Get info for sidebar
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        // Lưu lịch sử xem tài liệu
        viewHistoryService.saveViewHistory(userService.getCurrentUser(), doc);

        return "user/view-doc";
    }

    // endpoint for upload document page
    @GetMapping("/upload-document")
    public String showUploadDocumentPage(Model model) {
        // Get info for sidebar
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/upload";
    }

    @PostMapping("/upload-document")
    public String uploadDocument(@RequestParam("title") String title,
                                 @RequestParam("university") String university,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("thumbnail") MultipartFile thumbnail,
                                 @RequestParam("authorName") String authorName,
                                 @RequestParam("tags") String tags,
                                 Model model) {
        try {
            // Kiểm tra file tài liệu và thumbnail
            if (file.isEmpty() || thumbnail.isEmpty()) {
                throw new IllegalArgumentException("Both document and thumbnail files are required.");
            }

            // Kiểm tra MIME type của tài liệu
            String contentType = file.getContentType();
            if (!contentType.equals("application/pdf") &&
                    !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                throw new IllegalArgumentException("Invalid file type. Only PDF and DOCX are allowed.");
            }

            // Tạo đối tượng Document
            Document document = new Document();
            document.setName(title);
            document.setOwner(userService.getCurrentUser());
            document.setUniversity(university);
            document.setCateTags(tags);
            document.setVIP(userService.getCurrentUser().isVIP());
            document.setDateUploaded(LocalDateTime.now());

            // Lưu tài liệu vào thư mục uploads/documents
            String documentDir = "uploads/documents/";
            String documentFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path documentPath = Paths.get(documentDir);
            if (!Files.exists(documentPath)) {
                Files.createDirectories(documentPath);
            }
            Path savedDocumentPath = documentPath.resolve(documentFileName);
            Files.copy(file.getInputStream(), savedDocumentPath, StandardCopyOption.REPLACE_EXISTING);
            document.setPath("documents/" + documentFileName);
            document.setFileType(contentType);
            document.setSize(file.getSize());

            // Lưu thumbnail vào thư mục uploads/thumbnails
            String thumbnailDir = "uploads/thumbnails/";
            String thumbnailFileName = System.currentTimeMillis() + "_" + thumbnail.getOriginalFilename();
            Path thumbnailPath = Paths.get(thumbnailDir);
            if (!Files.exists(thumbnailPath)) {
                Files.createDirectories(thumbnailPath);
            }
            Path savedThumbnailPath = thumbnailPath.resolve(thumbnailFileName);
            Files.copy(thumbnail.getInputStream(), savedThumbnailPath, StandardCopyOption.REPLACE_EXISTING);
            document.setBackground("thumbnails/" + thumbnailFileName);

            // Lưu document vào cơ sở dữ liệu
            documentService.saveOrUpdateDocument(document);

            // Chuyển hướng về trang tài liệu của tôi
            return "redirect:/user/my-documents";

        } catch (IllegalArgumentException e) {
            // Trường hợp lỗi với thông báo người dùng
            model.addAttribute("error", e.getMessage());
            return "user/upload";

        } catch (IOException e) {
            // Lỗi khi lưu file
            e.printStackTrace();
            model.addAttribute("error", "Error saving files. Please try again.");
            return "user/upload";
        }
    }
}
