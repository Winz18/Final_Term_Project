package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Download;
import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.DownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DownloadService {

    private final DownloadRepository downloadRepository;

    @Autowired
    public DownloadService(DownloadRepository downloadRepository) {
        this.downloadRepository = downloadRepository;
    }

    // Lấy tất cả các lượt tải xuống
    public List<Download> getAllDownloads() {
        return downloadRepository.findAll();
    }

    // Tìm lượt tải xuống bằng ID
    public Optional<Download> getDownloadById(Long downloadId) {
        if (downloadId == null || downloadId <= 0) {
            throw new IllegalArgumentException("Download ID must be a positive number.");
        }
        return downloadRepository.findById(downloadId);
    }

    // Lấy danh sách lượt tải của một user
    public List<Download> getDownloadsByUser(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return downloadRepository.findByUser(user);
    }

    // Lấy danh sách lượt tải của một tài liệu
    public List<Download> getDownloadsByDocument(Document document) {
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return downloadRepository.findByDocument(document);
    }

    // Đếm số lượt tải của một tài liệu
    public long countDownloadsByDocument(Document document) {
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return downloadRepository.countByDocument(document);
    }

    // Đếm số lượt tải của một user
    public long countDownloadsByUser(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return downloadRepository.countByUser(user);
    }

    // Thêm một lượt tải xuống
    public Download addDownload(User user, Document document) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }

        Download download = new Download();
        download.setUser(user);
        download.setDocument(document);
        download.setTime(LocalDateTime.now());

        return downloadRepository.save(download);
    }

    // Xóa lượt tải xuống theo ID
    public void deleteDownloadById(Long downloadId) {
        if (downloadId == null || downloadId <= 0) {
            throw new IllegalArgumentException("Download ID must be a positive number.");
        }
        if (!downloadRepository.existsById(downloadId)) {
            throw new IllegalArgumentException("Download with ID " + downloadId + " does not exist.");
        }
        downloadRepository.deleteById(downloadId);
    }

    // Kiểm tra tính hợp lệ của một lượt tải xuống
    private void validateDownload(Download download) {
        if (download == null) {
            throw new IllegalArgumentException("Download cannot be null.");
        }
        if (download.getUser() == null || download.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Download must be associated with a valid user.");
        }
        if (download.getDocument() == null || download.getDocument().getDocId() == null) {
            throw new IllegalArgumentException("Download must be associated with a valid document.");
        }
        if (download.getTime() == null) {
            throw new IllegalArgumentException("Download time cannot be null.");
        }
    }

    public boolean userHasDownloadedDocument(User currentUser, Document doc) {
        if (currentUser == null || currentUser.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (doc == null || doc.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return downloadRepository.existsByUserAndDocument(currentUser, doc);
    }
}
