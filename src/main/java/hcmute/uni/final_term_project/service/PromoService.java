package hcmute.uni.final_term_project.service;

import hcmute.uni.final_term_project.entity.Promo;
import hcmute.uni.final_term_project.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromoService {

    private final PromoRepository promoRepository;

    @Autowired
    public PromoService(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    // Lấy tất cả các mã khuyến mãi
    public List<Promo> getAllPromos() {
        return promoRepository.findAll();
    }

    // Tìm mã khuyến mãi bằng ID
    public Optional<Promo> getPromoById(Long promoId) {
        if (promoId == null || promoId <= 0) {
            throw new IllegalArgumentException("Promo ID must be a positive number.");
        }
        return promoRepository.findById(promoId);
    }

    // Tìm mã khuyến mãi bằng mã code
    public Promo getPromoByCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Promo code cannot be null or empty.");
        }
        return promoRepository.findByCode(code);
    }

    // Lấy danh sách các mã khuyến mãi đang hoạt động
    public List<Promo> getActivePromos() {
        return promoRepository.findByEndDateAfterAndStartDateBefore(LocalDateTime.now(), LocalDateTime.now());
    }

    // Tạo hoặc cập nhật mã khuyến mãi
    public Promo saveOrUpdatePromo(Promo promo) {
        validatePromo(promo);
        return promoRepository.save(promo);
    }

    // Xóa mã khuyến mãi theo ID
    public void deletePromoById(Long promoId) {
        if (promoId == null || promoId <= 0) {
            throw new IllegalArgumentException("Promo ID must be a positive number.");
        }
        if (!promoRepository.existsById(promoId)) {
            throw new IllegalArgumentException("Promo with ID " + promoId + " does not exist.");
        }
        promoRepository.deleteById(promoId);
    }

    // Áp dụng mã khuyến mãi (giảm số lần sử dụng còn lại)
    public Promo applyPromo(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Promo code cannot be null or empty.");
        }
        Promo promo = promoRepository.findByCode(code);
        if (promo == null) {
            throw new IllegalArgumentException("Promo with code " + code + " does not exist.");
        }
        if (promo.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Promo with code " + code + " has expired.");
        }
        if (promo.getUses() >= promo.getLimitUsage()) {
            throw new IllegalArgumentException("Promo with code " + code + " has reached its usage limit.");
        }

        promo.setUses(promo.getUses() + 1);
        return promoRepository.save(promo);
    }

    // Kiểm tra thông tin promo hợp lệ
    private void validatePromo(Promo promo) {
        if (promo == null) {
            throw new IllegalArgumentException("Promo cannot be null.");
        }
        if (promo.getStartDate() == null || promo.getEndDate() == null) {
            throw new IllegalArgumentException("Promo must have valid start and end dates.");
        }
        if (promo.getEndDate().isBefore(promo.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        if (promo.getCode() == null || promo.getCode().isBlank()) {
            throw new IllegalArgumentException("Promo code cannot be null or empty.");
        }
        if (promo.getLimitUsage() <= 0) {
            throw new IllegalArgumentException("Promo usage limit must be greater than zero.");
        }
        if (promo.getValue() == null || promo.getValue() <= 0) {
            throw new IllegalArgumentException("Promo value must be greater than zero.");
        }
    }
}
