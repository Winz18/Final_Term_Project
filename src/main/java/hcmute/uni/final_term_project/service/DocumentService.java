package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.DocumentRepository;
import hcmute.uni.final_term_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    // Lấy tất cả tài liệu
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // Tìm tài liệu theo ID
    public Optional<Document> getDocumentById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Document ID must be a positive number.");
        }
        return documentRepository.findById(id);
    }

    // Lấy tài liệu theo owner
    public List<Document> getDocumentsByOwner(User owner) {
        if (owner == null || userRepository.findById(owner.getUserId()).isEmpty()) {
            throw new IllegalArgumentException("Owner must be a valid and existing user.");
        }
        return documentRepository.findByOwner(owner);
    }

    // Tìm tài liệu theo tên
    public List<Document> searchDocumentsByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty.");
        }
        return documentRepository.findByNameContaining(keyword);
    }

    // Lấy tài liệu theo trường đại học
    public List<Document> getDocumentsByUniversity(String university) {
        if (university == null || university.isBlank()) {
            throw new IllegalArgumentException("University cannot be null or empty.");
        }
        return documentRepository.findByUniversity(university);
    }

    // Lấy tài liệu theo danh mục hashtag
    public List<Document> getDocumentsByCategoryTag(String tag) {
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("Category tag cannot be null or empty.");
        }
        return documentRepository.findByCateTagsContaining(tag);
    }

    public List<Document> getVIPDocuments() {
        return documentRepository.findByIsVIP(true); // Lấy danh sách tài liệu VIP
    }

    public List<Document> getNonVIPDocuments() {
        return documentRepository.findByIsVIP(false); // Lấy danh sách tài liệu không phải VIP
    }

    // Tạo hoặc cập nhật tài liệu
    public Document saveOrUpdateDocument(Document document) {
        validateDocument(document);
        return documentRepository.save(document);
    }

    // Xóa tài liệu theo ID
    public void deleteDocumentById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Document ID must be a positive number.");
        }
        if (!documentRepository.existsById(id)) {
            throw new IllegalArgumentException("Document with ID " + id + " does not exist.");
        }
        documentRepository.deleteById(id);
    }

    // Cập nhật lượt xem tài liệu
    public void incrementViews(Long documentId) {
        if (documentId == null || documentId <= 0) {
            throw new IllegalArgumentException("Document ID must be a positive number.");
        }
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isEmpty()) {
            throw new IllegalArgumentException("Document with ID " + documentId + " does not exist.");
        }
        Document document = optionalDocument.get();
        document.setViews(document.getViews() + 1);
        documentRepository.save(document);
    }

    // Cập nhật lượt tải xuống
    public void incrementDownloads(Long documentId) {
        if (documentId == null || documentId <= 0) {
            throw new IllegalArgumentException("Document ID must be a positive number.");
        }
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isEmpty()) {
            throw new IllegalArgumentException("Document with ID " + documentId + " does not exist.");
        }
        Document document = optionalDocument.get();
        document.setDownloads(document.getDownloads() + 1);
        documentRepository.save(document);
    }

    // Kiểm tra tính hợp lệ của Document
    private void validateDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        if (document.getName() == null || document.getName().isBlank()) {
            throw new IllegalArgumentException("Document name cannot be null or empty.");
        }
        if (document.getUniversity() == null || document.getUniversity().isBlank()) {
            throw new IllegalArgumentException("University cannot be null or empty.");
        }
        if (document.getOwner() == null || userRepository.findById(document.getOwner().getUserId()).isEmpty()) {
            throw new IllegalArgumentException("Document owner must be a valid and existing user.");
        }
    }

    // Lấy danh sách tài liệu được đề xuất
    public List<Document> getRecommendedDocuments() {
        return documentRepository.findTop3ByOrderByDownloadsDesc();
    }

    // Lấy số lượt xem của tài liệu
    public int getDocumentViewsCount() {
        return documentRepository.findAll().stream().mapToInt(Document::getViews).sum();
    }

    // Lấy số lượt tải xuống của tài liệu
    public int getDocumentDownloadsCount() {
        return documentRepository.findAll().stream().mapToInt(Document::getDownloads).sum();
    }

    // lấy so luot like cua tai lieu
    public int getDocumentLikesCount() {
        return documentRepository.findAll().stream().mapToInt(Document::getLikes).sum();
    }

}
