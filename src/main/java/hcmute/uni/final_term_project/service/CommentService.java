package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Comment;
import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Lấy tất cả các bình luận
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Lấy bình luận theo ID
    public Optional<Comment> getCommentById(Long commentId) {
        if (commentId == null || commentId <= 0) {
            throw new IllegalArgumentException("Comment ID must be a positive number.");
        }
        return commentRepository.findById(commentId);
    }

    // Lấy danh sách bình luận của một tài liệu
    public List<Comment> getCommentsByDocument(Document document) {
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return commentRepository.findByDocument(document);
    }

    // Đếm số bình luận của một tài liệu
    public long countCommentsByDocument(Document document) {
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return commentRepository.countByDoc(document);
    }

    // Thêm bình luận mới
    public Comment addComment(User user, Document document, String content) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setDocument(document);
        comment.setContent(content.trim());
        comment.setDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    // Xóa bình luận theo ID
    public void deleteCommentById(Long commentId) {
        if (commentId == null || commentId <= 0) {
            throw new IllegalArgumentException("Comment ID must be a positive number.");
        }
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Comment with ID " + commentId + " does not exist.");
        }
        commentRepository.deleteById(commentId);
    }

    // Kiểm tra tính hợp lệ của một bình luận
    private void validateComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null.");
        }
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be null or empty.");
        }
        if (comment.getUser() == null || comment.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Comment must be associated with a valid user.");
        }
        if (comment.getDocument() == null || comment.getDocument().getDocId() == null) {
            throw new IllegalArgumentException("Comment must be associated with a valid document.");
        }
        if (comment.getDate() == null) {
            throw new IllegalArgumentException("Comment date cannot be null.");
        }
    }
}
