package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Document;
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

    // Tìm tài liệu theo trạng thái VIP
    public List<Document> getVIPDocuments() {
        return documentRepository.findByIsVIP();
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
        if (document.getFileType() == null || !(document.getFileType().equalsIgnoreCase("pdf") || document.getFileType().equalsIgnoreCase("docx"))) {
            throw new IllegalArgumentException("File type must be either 'pdf' or 'docx'.");
        }
        if (document.getOwner() == null || userRepository.findById(document.getOwner().getUserId()).isEmpty()) {
            throw new IllegalArgumentException("Document owner must be a valid and existing user.");
        }
    }
}
