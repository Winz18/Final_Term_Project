package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.entity.ViewHistory;
import hcmute.uni.final_term_project.repository.ViewHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViewHistoryService {
    private final ViewHistoryRepository viewHistoryRepository;

    @Autowired
    public ViewHistoryService(ViewHistoryRepository viewHistoryRepository) {
        this.viewHistoryRepository = viewHistoryRepository;
    }

    // Lấy danh sách tài liệu đã xem gần đây, không trùng lặp
    public List<ViewHistory> getDistinctViewHistoryByUser(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return viewHistoryRepository.findDistinctDocumentsByUserOrderByViewedAtDesc(user);
    }

    // Lấy top 3 tài liệu đã xem gần đây, không trùng lặp
    public List<ViewHistory> getTop3DistinctViewHistoryByUser(User user) {
        return getDistinctViewHistoryByUser(user).stream()
                .limit(3)
                .toList();
    }
}
