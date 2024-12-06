package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Commission;
import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommissionService {

    private final CommissionRepository commissionRepository;

    @Autowired
    public CommissionService(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    // Lấy tất cả hoa hồng
    public List<Commission> getAllCommissions() {
        return commissionRepository.findAll();
    }

    // Tìm hoa hồng theo ID
    public Optional<Commission> getCommissionById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Commission ID must be a positive number.");
        }
        return commissionRepository.findById(id);
    }

    // Lấy danh sách hoa hồng theo User
    public List<Commission> getCommissionsByUser(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        return commissionRepository.findByUser(user);
    }

    // Lấy danh sách hoa hồng theo Document
    public List<Commission> getCommissionsByDocument(Document document) {
        if (document == null || document.getDocId() == null) {
            throw new IllegalArgumentException("Document cannot be null or without an ID.");
        }
        return commissionRepository.findByDocument(document);
    }

    // Lấy danh sách hoa hồng theo User trong một khoảng thời gian
    public List<Commission> getCommissionsByUserAndDateRange(User user, LocalDate start, LocalDate end) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Invalid date range.");
        }
        return commissionRepository.findByUserAndDateBetween(user, start, end);
    }

    // Tính tổng giá trị hoa hồng của User trong một khoảng thời gian
    public double getTotalCommissionValueByUserAndDateRange(User user, LocalDate start, LocalDate end) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User cannot be null or without an ID.");
        }
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Invalid date range.");
        }
        return commissionRepository.sumValueByUserAndDateBetween(user, start, end);
    }

    // Tạo hoặc cập nhật hoa hồng
    public Commission saveOrUpdateCommission(Commission commission) {
        validateCommission(commission);
        return commissionRepository.save(commission);
    }

    // Xóa hoa hồng theo ID
    public void deleteCommissionById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Commission ID must be a positive number.");
        }
        if (!commissionRepository.existsById(id)) {
            throw new IllegalArgumentException("Commission with ID " + id + " does not exist.");
        }
        commissionRepository.deleteById(id);
    }

    // Kiểm tra tính hợp lệ của Commission
    private void validateCommission(Commission commission) {
        if (commission == null) {
            throw new IllegalArgumentException("Commission cannot be null.");
        }
        if (commission.getValue() == null || commission.getValue() <= 0) {
            throw new IllegalArgumentException("Commission value must be greater than zero.");
        }
        if (commission.getDate() == null) {
            throw new IllegalArgumentException("Commission date cannot be null.");
        }
        if (commission.getUser() == null || commission.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Commission must be linked to a valid user.");
        }
        if (commission.getDocument() == null || commission.getDocument().getDocId() == null) {
            throw new IllegalArgumentException("Commission must be linked to a valid document.");
        }
    }
}
