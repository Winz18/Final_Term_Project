package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Likes;
import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    // Lấy tất cả các lượt like
    public List<Likes> getAllLikes() {
        return likesRepository.findAll();
    }

    // Tìm lượt like bằng ID
    public Optional<Likes> getLikeById(Long likeId) {
        if (likeId == null || likeId <= 0) {
            throw new IllegalArgumentException("Like ID must be a positive number.");
        }
        return likesRepository.findById(likeId);
    }

    // Lấy danh sách lượt like của một user
    public List<Likes> getLikesByUser(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return likesRepository.findByUser(user);
    }

    // Lấy danh sách lượt like của một tài liệu
    public List<Likes> getLikesByDocument(Document document) {
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return likesRepository.findByDocument(document);
    }

    // Đếm số lượt like của một tài liệu
    public long countLikesByDocument(Document document) {
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return likesRepository.countByDocument(document);
    }

    // Kiểm tra xem một user đã like tài liệu chưa
    public boolean userHasLikedDocument(User user, Document document) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return likesRepository.existsByUserAndDocument(user, document);
    }

    // Thêm một lượt like
    public Likes addLike(User user, Document document) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        if (userHasLikedDocument(user, document)) {
            throw new IllegalArgumentException("User has already liked this document.");
        }

        Likes like = new Likes();
        like.setUser(user);
        like.setDocument(document);
        like.setTime(LocalDateTime.now());

        return likesRepository.save(like);
    }

    // Xóa một lượt like
    public void removeLike(User user, Document document) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        if (!userHasLikedDocument(user, document)) {
            throw new IllegalArgumentException("User has not liked this document.");
        }

        Likes like = likesRepository.findByUser(user).stream()
                .filter(l -> l.getDocument().getDocId().equals(document.getDocId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Like not found."));
        likesRepository.delete(like);
    }
}
